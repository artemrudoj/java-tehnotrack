package ru.mipt.chat;

import ru.mipt.message.Message;
import ru.mipt.session.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by artem on 16.11.15.
 */
public abstract class Chat {

    public static final long MESSAGE_ONLY_FOR_SERVER = -1;
    long chatId;
    LinkedList<Long> messages;
    LinkedList<Long> participantIds;
    long admin;

    public Chat() {
        messages = new LinkedList<>();
        participantIds = new LinkedList<>();
    }

    abstract void addMessage(Message message);

    abstract void addParticipant(Long userId);

    abstract LinkedList<Long> getParticipantIds();

    abstract LinkedList<Long> getMessagesIds();

    abstract  long getChatId();

    abstract public long getAdmin();

    abstract public void setAdmin(long admin);
}
