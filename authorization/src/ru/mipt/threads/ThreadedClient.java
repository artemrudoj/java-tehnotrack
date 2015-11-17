package ru.mipt.threads;


import ru.mipt.comands.CommandType;
import ru.mipt.message.ReturnCode;
import ru.mipt.message.Message;
import ru.mipt.session.User;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";
    private MessageValidator validator;

    ConnectionHandler handler;
    long sessionId;
    User user;

    public ThreadedClient() {
        validator = new MessageValidator();
        sessionId = ReturnCode.NO_CURRENT_SESSION;
        try {
            Socket socket = new Socket(HOST, PORT);
            handler = new SocketConnectionHandler(socket);
            handler.addListener(this);
            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void processInput(String line) throws IOException {
        long userId;
        if (user == null)
            userId = User.NO_USER_ID;
        else
            userId = user.getUserId();
        Message msg = validator.prepareMessage(line, userId, sessionId);
        if (msg == null) {
            System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(ReturnCode.COMMAND_NOT_FOUNDED));
            return;
        }
        handler.send(msg);
    }

    @Override
    public void onMessage(Message msg) {
        switch (msg.getType()) {
            case CommandType.SIMPLE_MESSAGE:
                System.out.printf("[chat id = %d ] : [user id = %d]:%s\n", msg.getChatId(), msg.getSenderId(), msg.getMessage());
                break;
            case CommandType.LOGIN:
                if (msg.getReturnCode() == ReturnCode.SUCCESS) {
                    assert (msg != null);
                    String[] tokens = msg.getMessage().split(" ");
                    assert (tokens.length == 3);
                    user = new User(tokens[0], tokens[1], Long.parseLong(tokens[2]));
                    sessionId = msg.getSessionId();
                    System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(msg.getReturnCode()));
                    return;
                }
                System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(msg.getReturnCode()));
                break;
            default:
                System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(msg.getReturnCode()) + msg.getMessage());
        }
    }


    public static void main(String[] args) throws Exception{
        ThreadedClient client = new ThreadedClient();
        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
            String input = scanner.nextLine();
            if ("q".equals(input)) {
                return;
            }
            client.processInput(input);
        }
    }

}