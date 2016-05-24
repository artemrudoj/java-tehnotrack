package ru.mipt.threads;



import com.mchange.v2.c3p0.ComboPooledDataSource;
import ru.mipt.authorization.AuthorizationService;
import ru.mipt.authorization.DataBaseUserStore;
import ru.mipt.authorization.SimpleUserStore;
import ru.mipt.authorization.UserStore;
import ru.mipt.chat.DataBaseChatStorage;
import ru.mipt.comands.*;
import ru.mipt.messagestore.DataBaseMessageStore;
import ru.mipt.messagestore.MessageStore;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.chat.Chat;
import ru.mipt.chat.ChatStorage;
import ru.mipt.session.SessionStorage;
import ru.mipt.threadstrorage.HashMapThreadIdStrorage;
import ru.mipt.threadstrorage.ThreadsIdStorage;
//import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private MessageStore messageStore;
    InputHandler inputHandler;
    SessionStorage sessions;

    public ComboPooledDataSource getConnectionPool() {
        return connectionPool;
    }

    private ComboPooledDataSource connectionPool;


    public ThreadedServer() {
        try {
            Class.forName("org.postgresql.Driver");
            connectionPool = new ComboPooledDataSource();
            connectionPool.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
            connectionPool.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/internet_tech_db");
            connectionPool.setUser("postgres");
            connectionPool.setPassword("123");
            connectionPool.setMinPoolSize(5);
            connectionPool.setAcquireIncrement(5);
            connectionPool.setMaxPoolSize(20);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public void startServer() throws Exception {

        Map<String, Command> commands = new HashMap<>();
        internalCounter = new AtomicLong(0);
        handlers = new HashMap<>();
        threadIdStrorage = new HashMapThreadIdStrorage();
        sessions = new SessionStorage();
        chatStorage = new DataBaseChatStorage(connectionPool);
        messageStore = new DataBaseMessageStore(connectionPool);
        userStore = new DataBaseUserStore(connectionPool);

        AuthorizationService authService = new AuthorizationService(userStore);

        //chatStorage = new SimpleChatStorage();
        //messageStore = new BasedOnListStorage();



        Command loginCommand = new LoginCommand(authService, sessions);
        Command registrationCommand = new RegistrationCommand(authService);

        Command userCommand = new UserCommand(userStore);
        Command chatCommand = new ChatCommand(chatStorage, userStore, messageStore);

        commands.put("\\user", userCommand);
        commands.put("\\login", loginCommand);
        commands.put("\\registration", registrationCommand);

        commands.put("\\chat", chatCommand);



        inputHandler = new InputHandler(commands);

        isRunning = true;
        while (isRunning) {
            if(sSocket.isClosed())
                return;
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
//        for (ConnectionHandler handler : handlers.values()) {
//            handler.stop();
//        }
        try {
            sSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            ArrayList<Long> participants = chatStorage.getParticipantIds(sendedMessage.getChatId());
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