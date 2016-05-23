package ru.mipt.messagestore;

import ru.mipt.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by artem on 18.10.15.
 */
public class BasedOnListStorage implements MessageStore {

    LinkedList<Message> listStorage;
    private AtomicLong internalCounter;

    public BasedOnListStorage() {
        listStorage = new LinkedList<>();
        internalCounter = new AtomicLong(0);
    }


    @Override
    public long addMessage(Message msg) {
        long messageId = internalCounter.incrementAndGet();
        listStorage.add(msg);
        return messageId;
    }

    @Override
    public Message getMessageById(long id) {
        for (Message message : listStorage) {
            if (message.getMessageId() == id)
                return message;
        }
        return null;
    }

    @Override
    public ArrayList<Message> findMessage(String msg, long chatId) {
        return null;
    }

    @Override
    public ArrayList<Message> getMessages(long chatId) {
        return null;
    }
}
