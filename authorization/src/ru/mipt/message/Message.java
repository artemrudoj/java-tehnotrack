package ru.mipt.message;

import java.io.Serializable;

/**
 * Created by artem on 19.10.15.
 */
public class Message  implements Serializable {

    long sessionId;
    long time;
    long senderId;
    long chatId;
    short messageType;
    long messageId;
    String message;

    public Message(short messageType, String message, long time) {
        this.messageType = messageType;
        this.message = message;
        this.time = time;
    }

    public Message(String line) {
        message = line;
    }

    public Message(short returnCode) {
        messageType = returnCode;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public short getMessageType() {
        return messageType;
    }

    public void setMessageType(short messageType) {
        this.messageType = messageType;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}

