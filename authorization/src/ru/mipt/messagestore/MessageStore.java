package ru.mipt.messagestore;

import ru.mipt.message.Message;

import java.util.ArrayList;


/**
 * Created by artem on 18.10.15.
 */
public interface MessageStore {

    long addMessage(Message msg);
    Message getMessageById(long id);
    ArrayList<Message> findMessage(String msg, long chatId);
    ArrayList<Message> getMessages(long chatId);

}
