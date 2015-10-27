package ru.mipt.hisorystorage;

/**
 * Created by artem on 19.10.15.
 */
public class Message {

    String message;
    long time;
    final int messageType;
    int id;

    public Message(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public Message(int messageType) {
        this.messageType = messageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public String PrintMessageWith() {
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

