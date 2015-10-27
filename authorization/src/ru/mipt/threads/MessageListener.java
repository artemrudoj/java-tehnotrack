package ru.mipt.threads;


import ru.mipt.hisorystorage.Message;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Message message);
}