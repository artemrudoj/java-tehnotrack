package ru.mipt.session;

import ru.mipt.messagestore.MessageStore;


public class Session {

    private User sessionUser;
    private MessageStore messageStore;
    private int sessionId;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Session(MessageStore storage) {
        messageStore = storage;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public boolean isSeesionExist() { return sessionUser != null; }

    public MessageStore getMessageStore() {
        return messageStore;
    }
}
