package ru.mipt.threads;


import ru.mipt.protocol.Message;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Message message);
}