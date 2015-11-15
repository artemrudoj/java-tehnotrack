package ru.mipt.chat;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by artem on 15.11.15.
 */

public class SimpleChatStorage implements ChatStorage {

    HashMap<Long, SimpleChat> chatHashMap;
    private AtomicLong internalCounter;

    public SimpleChatStorage() {
        chatHashMap = new HashMap<>();
        internalCounter = new AtomicLong(0);
    }


    @Override
    public SimpleChat getChat(long id) {
        return chatHashMap.get(id);
    }
    @Override
    public long addChat(Chat chat) {
        long chatId = internalCounter.incrementAndGet();
        chatHashMap.put(chatId, (SimpleChat)chat);
        return chatId;
    }
}