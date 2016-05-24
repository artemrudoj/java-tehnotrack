package ru.mipt.threads;

import ru.mipt.comands.CommandType;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.protocol.Protocol;
import ru.mipt.session.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class SocketConnectionHandler implements ConnectionHandler {


    private List<MessageListener> listeners = new ArrayList<>();
    private InputStream in;
    private OutputStream out;
    long sessionId;
    User user;
    private MessageValidator validator;

    public SocketConnectionHandler(Socket socket) throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        validator = new MessageValidator();
        sessionId = ReturnCode.NO_CURRENT_SESSION;
       // addListener(this);
    }

    public void setSessionId(Long id) {
        sessionId = id;
    }

    @Override
    public void send(Message msg) throws IOException {
        String string = decode(msg);
        byte[] b = string.getBytes(StandardCharsets.UTF_8);
        out.write(b);
        out.flush();
    }

    // Добавить еще подписчика
    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }


    // Разослать всем
    public void notifyListeners(Message msg) {
        listeners.forEach(it -> it.onMessage(msg));
    }

    @Override
    public void run() {
        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                if (read > 0) {

                    //Message msg = Protocol.decode(Arrays.copyOf(buf, read));
                    Message msg = processInput(Arrays.copyOf(buf, read));
                    //log.info("message received: {}", msg);

                    notifyListeners(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public Message processInput(byte[] bytes) throws IOException {
        long userId;
        String templine  =  new String(bytes, StandardCharsets.UTF_8);
        String line =  templine.substring(0, templine.length() - 2);
        if (user == null)
            userId = User.NO_USER_ID;
        else
            userId = user.getUserId();
        return  validator.prepareMessage(line, userId, sessionId);
//        if (msg == null) {
//            System.out.printf("%s\n", ReturnCode.getReturnCodeInfo(ReturnCode.COMMAND_NOT_FOUNDED));
//            return;
//        }
//        send(msg);
    }


    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }


    public String decode(Message msg) {
        switch (msg.getType()) {
            case CommandType.SIMPLE_MESSAGE:
                if( msg.getReturnCode() == ReturnCode.SUCCESS)
                    return  String.format("[chat id = %d ] : [user id = %d]:%s\n", msg.getChatId(), msg.getSenderId(), msg.getMessage());
                break;
            case CommandType.LOGIN:
                if (msg.getReturnCode() == ReturnCode.SUCCESS) {
                    assert (msg != null);
                    String[] tokens = msg.getMessage().split(" ");
                    assert (tokens.length == 3);
                    user = new User(tokens[0], tokens[1], Long.parseLong(tokens[2]));
                    sessionId = msg.getSessionId();
                    break;
                }
                break;
        }
        return String.format("%s\n", validator.messageDecarator(msg));
    }
}