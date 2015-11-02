package ru.mipt.threads;



import ru.mipt.InputHandler;
import ru.mipt.authorization.AuthorizationService;
import ru.mipt.authorization.UserStore;
import ru.mipt.comands.*;
import ru.mipt.hisorystorage.BasedOnListStorage;
import ru.mipt.hisorystorage.HistoryStorage;
import ru.mipt.protocol.Message;
import ru.mipt.session.Chat;
import ru.mipt.session.SessionStorage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class ThreadedServer implements MessageListener {

    //static Logger log = LoggerFactory.getLogger(ThreadedServer.class);

    public static final int PORT = 19000;
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private Map<Long,Long> threadIdStrorage = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    InputHandler inputHandler;
    SessionStorage sessions;

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
        int threadId = handler
        return Long.valueOf(0);
    }

    private ConnectionHandler getHandlerBySessionId(Long sessionId) {
        return null;
    }

    private void startServer() throws Exception {
        //log.info("Started, waiting for connection");

        Map<String, Command> commands = new HashMap<>();


        HistoryStorage historyStorage= new BasedOnListStorage();


        UserStore userStore = new UserStore();
        AuthorizationService authService = new AuthorizationService(userStore);


        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand();
        Command userCommand = new UserCommand();
        Command findCommand = new FindCommand();

        commands.put("\\find", findCommand);
        commands.put("\\user", userCommand);
        commands.put("\\history", historyCommand);
        commands.put("\\login", loginCommand);
        commands.put("\\help", helpCommand);


        inputHandler = new InputHandler(commands);

        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();

            long sessionId = internalCounter.incrementAndGet();
            SocketConnectionHandler handler = new SocketConnectionHandler(socket);
            handler.addListener(this);

            handlers.put(sessionId, handler);
            Thread thread = new Thread(handler);

            threadIdStrorage.put(thread.getId(), sessionId);

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
        short messageType = message.getMessageType();
        long rcvedSessionId = message.getSessionId();
        //populate body of new sending message
        Message sendedMessage = inputHandler.handle(message.getMessage(), sessions.getSessionById(rcvedSessionId));

        //if no success in perfoming operation or message only to server


        if ((sendedMessage.getMessageType() != ReturnCode.SUCCESS) ||
                (sendedMessage.getChatId() == Chat.MESSAGE_ONLY_FOR_SERVER )) {
            Long currentSessionId = threadIdStrorage.get(Thread.currentThread().getId());
            ConnectionHandler handler = handlers.get(currentSessionId);

            //for login send sessionId
            if (rcvedSessionId == ReturnCode.NO_CURRENT_SESSION) {
                sendedMessage.setSessionId(currentSessionId);
            }

            try {
                handler.send(sendedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (message.getChatId() == Chat.MESSAGE_ONLY_FOR_SERVER);
            for (ConnectionHandler handler : handlers.values()) {
                try {
                    handler.send(message);
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