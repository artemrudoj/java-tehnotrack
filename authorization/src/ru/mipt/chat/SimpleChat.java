package ru.mipt.chat;

import ru.mipt.message.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by artem on 28.10.15.
 */
public class SimpleChat extends Chat {

    LinkedList<Message> messagesStore;


    public SimpleChat() {
        super();
        messagesStore = new LinkedList<>();
    }

    @Override
    void addMessage(Message message) {
        messagesStore.add(message);
        messages.add(message.getMessageId());
    }

    @Override
    public void addParticipant(Long userId) {
        participantIds.add(userId);
    }

    @Override
    public LinkedList<Long> getParticipantIds() {
        return participantIds;
    }

    @Override
    public LinkedList<Long> getMessagesIds() {
        return messages;
    }

    @Override
    public long getChatId() {
        return chatId;
    }

    @Override
    public long getAdmin() {
        return 0;
    }

    @Override
    public void setAdmin(long admin) {
    }

    public LinkedList<Message> getMessagesStore() {
        return messagesStore;
    }
}
