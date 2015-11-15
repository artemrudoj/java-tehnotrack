package ru.mipt.chat;

/**
 * Created by artem on 29.10.15.
 */
public interface ChatStorage {
    long addChat(Chat chat);
    SimpleChat getChat(long id);
}
