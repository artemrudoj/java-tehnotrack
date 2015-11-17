package ru.mipt.chat;

import ru.mipt.message.Message;

import java.util.LinkedList;

/**
 * Created by artem on 28.10.15.
 */
public class Chat {
    public static final int MESSAGE_ONLY_FOR_SERVER = -1;
    LinkedList<Message> messagesStore;
    long chatId;
    LinkedList<Long> messages;
    LinkedList<Long> participantIds;
    long admin;

    public Chat() {
        messages = new LinkedList<>();
        participantIds = new LinkedList<>();
        messagesStore = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messagesStore.add(message);
        messages.add(message.getMessageId());
    }

    public void addParticipant(Long userId) {
        participantIds.add(userId);
    }

    public LinkedList<Long> getParticipantIds() {
        return participantIds;
    }

    public LinkedList<Long> getMessagesIds() {
        return messages;
    }

    public long getChatId() {
        return chatId;
    }

    public long getAdmin() {
        return 0;
    }

    public void setAdmin(long admin) {
    }

    public LinkedList<Message> getMessagesStore() {
        return messagesStore;
    }
}
