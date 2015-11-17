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
    short returnCode;
    short type;
    long messageId;
    String message;

    public Message(short returnCode, String message, long time) {
        this.returnCode = returnCode;
        this.message = message;
        this.time = time;
    }

    public Message(String line) {
        message = line;
    }

    public Message(short returnCode) {
        this.returnCode = returnCode;
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

    public short getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(short returnCode) {
        this.returnCode = returnCode;
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

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}

