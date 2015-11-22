package ru.mipt.messagestore;

import ru.mipt.message.Message;

import java.util.ArrayList;

/**
 * Created by artem on 18.10.15.
 */
public interface MessageStore {

    long addMessage(Message msg);

    ArrayList<Message> findMessage(String msg);

    ArrayList<Message> returnLastMessages(int N);

    Message getMessageById(long id);

}
