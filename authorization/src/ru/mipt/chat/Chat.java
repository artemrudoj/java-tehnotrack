package ru.mipt.chat;

import ru.mipt.message.Message;
import ru.mipt.messagestore.MessageStore;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by artem on 28.10.15.
 */
public class Chat {
    public static final int MESSAGE_ONLY_FOR_SERVER = -1;
    long chatId;
    LinkedList<Long> messages;
    LinkedList<Long> participantIds;
    long admin;

    public Chat() {
        messages = new LinkedList<>();
        participantIds = new LinkedList<>();
    }

    public void addMessage(Message message) {
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

    public ArrayList<Message> getMessages(MessageStore messageStore) {
        ArrayList<Message> messagesFromHistrory = new ArrayList<>();
        for (long id : messages) {
            Message message = messageStore.getMessageById(id);
            assert (message != null);
            messagesFromHistrory.add(message);
        }
        return messagesFromHistrory;
    }


    public ArrayList<Message> findMessage(String msg, MessageStore messageStore) {
        ArrayList<Message> findedMessages = new ArrayList<Message>();
        ArrayList<Message> messagesOfChat = getMessages(messageStore);
        for (Message currentMsg : messagesOfChat) {
            for (String data : currentMsg.getMessage().split(" ")) {
                if (msg.equals(data)) {
                    findedMessages.add(currentMsg);
                    break;
                }
            }
        }
        return findedMessages;
    }

    public long getChatId() {
        return chatId;
    }

    public long getAdmin() {
        return 0;
    }

    public void setAdmin(long admin) {
    }


}
