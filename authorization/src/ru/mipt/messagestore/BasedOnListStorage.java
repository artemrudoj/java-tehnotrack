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
    public ArrayList<Message> findMessage(String msg) {
        ArrayList<Message> findedMessages = new ArrayList<Message>();
        for (Message currentMsg : listStorage) {
            for (String data : currentMsg.getMessage().split(" ")) {
                if (msg.equals(data)) {
                    findedMessages.add(currentMsg);
                    break;
                }
            }
        }
        return findedMessages;
    }

    @Override
    public ArrayList<Message> returnLastMessages(int N) {
        ArrayList<Message> lastMessages = new ArrayList<Message>();
        Iterator<Message> iterator = listStorage.descendingIterator();
        for (int i = 0; i < N && iterator.hasNext(); i++) {
            lastMessages.add(iterator.next());
        }
        return  lastMessages;
    }

    @Override
    public Message getMessageById(long id) {
        for (Message message : listStorage) {
            if (message.getMessageId() == id)
                return message;
        }
        return null;
    }
}
