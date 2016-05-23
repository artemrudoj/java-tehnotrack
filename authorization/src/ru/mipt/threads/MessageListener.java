package ru.mipt.threads;


import ru.mipt.message.Message;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Message message);
}