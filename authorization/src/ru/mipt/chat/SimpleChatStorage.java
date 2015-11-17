package ru.mipt.chat;


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
    public Chat getChat(long id) {
        return chatHashMap.get(id);
    }


    @Override
    public LinkedList<Long> getChatsForUser(long userId) {
        LinkedList<Long> chats = new LinkedList<>();
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
    public long addChat(Chat chat) {
        long chatId = internalCounter.incrementAndGet();
        chatHashMap.put(chatId, (Chat)chat);
        return chatId;
    }
}