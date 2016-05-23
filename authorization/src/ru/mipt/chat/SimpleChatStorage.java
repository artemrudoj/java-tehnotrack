package ru.mipt.chat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by artem on 15.11.15.
 */

public class SimpleChatStorage implements ChatStorage {

    HashMap<Long, Chat> chatHashMap;
    private AtomicLong internalCounter;

    public SimpleChatStorage() {
        chatHashMap = new HashMap<>();
        internalCounter = new AtomicLong(0);
    }

    @Override
    public ArrayList<Long> getChatsForUser(long userId) {
        ArrayList<Long> chats = new ArrayList<>();
        for (Map.Entry<Long, Chat> entry : chatHashMap.entrySet()) {
            Chat chat = entry.getValue();
            LinkedList<Long> usersId = chat.getParticipantIds();
            if (usersId.indexOf(userId) != -1) {
                chats.add(entry.getKey());
            }
        }
        return chats;
    }

    @Override
    public boolean isChatExist(long userId, long chatId) {
//        Chat chat = getChat(chatId);
//        if (chat == null)
//            return false;
//        for (long id : chat.getParticipantIds()) {
//            if (id == userId)
//                return true;
//        }
        return false;
    }

    @Override
    public ArrayList<Long> getParticipantIds(long chatId) {
        return null;
    }

    @Override
    public long addChat(Chat chat) {
        long chatId = internalCounter.incrementAndGet();
        chatHashMap.put(chatId, (Chat)chat);
        return chatId;
    }
}