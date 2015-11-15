package ru.mipt.threads;


import ru.mipt.chat.Chat;
import ru.mipt.message.ReturnCode;
import ru.mipt.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";


    ConnectionHandler handler;

    public ThreadedClient() {
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
        Message msg = new Message(line);
        msg.setChatId(Chat.MESSAGE_ONLY_FOR_SERVER);
        handler.send(msg);
    }

    @Override
    public void onMessage(Message msg) {
        switch (msg.getMessageType()) {
            case ReturnCode.SIMPLE_MESSAGE:
     /*           System.out.printf("%s\n", msg.getMessage());
                break;*/
            case ReturnCode.SUCCESS:
               // parseMessage();
               // break;
            default:
                System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(msg.getMessageType()) + msg.getMessage());
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