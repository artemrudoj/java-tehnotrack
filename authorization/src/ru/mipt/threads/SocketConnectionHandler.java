package ru.mipt.threads;

import ru.mipt.message.Message;
import ru.mipt.protocol.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
    private Long sessionId;

    public SocketConnectionHandler(Socket socket) throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public void setSessionId(Long id) {
        sessionId = id;
    }

    @Override
    public void send(Message msg) throws IOException {
        out.write(Protocol.encode(msg));
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
                    Message msg = Protocol.decode(Arrays.copyOf(buf, read));

                    //log.info("message received: {}", msg);

                    notifyListeners(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }
}