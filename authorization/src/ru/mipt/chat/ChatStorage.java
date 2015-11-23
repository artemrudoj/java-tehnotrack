package ru.mipt.chat;

import java.util.ArrayList;


/**
 * Created by artem on 29.10.15.
 */
public interface ChatStorage {
    long addChat(Chat chat);
    ArrayList<Long> getChatsForUser(long userId);
    boolean isChatExist(long userId, long charId);

    ArrayList<Long> getParticipantIds(long chatId);
}
