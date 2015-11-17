package ru.mipt.threads;



import ru.mipt.authorization.AuthorizationService;
import ru.mipt.authorization.UserStore;
import ru.mipt.chat.SimpleChatStorage;
import ru.mipt.comands.*;
import ru.mipt.hisorystorage.BasedOnListStorage;
import ru.mipt.hisorystorage.HistoryStorage;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.chat.Chat;
import ru.mipt.chat.ChatStorage;
import ru.mipt.session.SessionStorage;
import ru.mipt.threadstrorage.HashMapThreadIdStrorage;
import ru.mipt.threadstrorage.ThreadsIdStorage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class ThreadedServer implements MessageListener {


    public static final int PORT = 19000;
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers;
    private ThreadsIdStorage threadIdStrorage;
    private AtomicLong internalCounter;
    private ServerSocket sSocket;
    private ChatStorage chatStorage;
    private UserStore userStore;
    InputHandler inputHandler;
    SessionStorage sessions;
    MessageValidator validator;

    public ThreadedServer() {
        try {
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private Long getSessionIdByThread(ConnectionHandler handler) {
        return Long.valueOf(0);
    }

    private ConnectionHandler getHandlerBySessionId(Long sessionId) {
        return null;
    }

    private void startServer() throws Exception {

        Map<String, Command> commands = new HashMap<>();
        HistoryStorage historyStorage= new BasedOnListStorage();
        internalCounter = new AtomicLong(0);
        handlers = new HashMap<>();
        threadIdStrorage = new HashMapThreadIdStrorage();
        sessions = new SessionStorage();
        userStore = new UserStore();
        AuthorizationService authService = new AuthorizationService(userStore);
        chatStorage = new SimpleChatStorage();
        validator = new MessageValidator();
        Command loginCommand = new LoginCommand(authService, sessions);
        Command registrationCommand = new RegistrationCommand(authService, sessions);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand();
        Command userCommand = new UserCommand();
        Command findCommand = new FindCommand();
        Command chatCommand = new ChatCommand(chatStorage, userStore);

        commands.put("\\find", findCommand);
        commands.put("\\user", userCommand);
        commands.put("\\history", historyCommand);
        commands.put("\\login", loginCommand);
        commands.put("\\registration", registrationCommand);
        commands.put("\\help", helpCommand);
        commands.put("\\chat", chatCommand);



        inputHandler = new InputHandler(commands);

        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();

            long sessionId = internalCounter.incrementAndGet();
            SocketConnectionHandler handler = new SocketConnectionHandler(socket);
            handler.setSessionId(sessionId);
            handler.addListener(this);

            handlers.put(sessionId, handler);
            Thread thread = new Thread(handler);
            threadIdStrorage.add(thread.getId(), sessionId);

            thread.start();
        }
    }

    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }



    @Override
    public void onMessage(Message message) {
        long recivedSessionId = message.getSessionId();
        //handle message

        Long currentSessionId = threadIdStrorage.getSessionId(Thread.currentThread().getId());
        //perfoem message
        Message sendedMessage = inputHandler.handle(message, sessions.getSessionById(recivedSessionId),
                        currentSessionId);

        LinkedList<ConnectionHandler> handlerLinkedList = new LinkedList<>();

        //if no success in perfoming operation or message only to server
        //sended back to client
       if ((sendedMessage.getReturnCode() != ReturnCode.SUCCESS) ||
                (sendedMessage.getChatId() == Chat.MESSAGE_ONLY_FOR_SERVER)) {

            ConnectionHandler handler = handlers.get(currentSessionId);
            handlerLinkedList.add(handler);
       } else {
            LinkedList<Long> participants = chatStorage.getChat(sendedMessage.getChatId()).getParticipantIds();
            for (long userId : participants) {
                if (userId == sendedMessage.getSenderId())
                    continue;
                ConnectionHandler handler;
                handler = handlers.get(sessions.getSessionIdByUserId(userId));
                if (handler != null)
                    handlerLinkedList.add(handler);
            }
        }

        for (ConnectionHandler handler : handlerLinkedList) {
            try {
                handler.send(sendedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        ThreadedServer server = new ThreadedServer();
        server.startServer();
    }
}