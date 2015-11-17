package ru.mipt.chat;

import java.util.LinkedList;

/**
 * Created by artem on 29.10.15.
 */
public interface ChatStorage {
    long addChat(Chat chat);
    Chat getChat(long id);
    LinkedList<Long> getChatsForUser(long userId);
}
