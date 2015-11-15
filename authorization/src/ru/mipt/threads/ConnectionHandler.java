package ru.mipt.threads;

import ru.mipt.message.Message;

import java.io.IOException;




public interface ConnectionHandler extends Runnable {

    void send(Message msg) throws IOException;

    void addListener(MessageListener listener);

    void stop();


}