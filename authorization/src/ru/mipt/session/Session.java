package ru.mipt.session;

import ru.mipt.hisorystorage.HistoryStorage;


public class Session {

    private User sessionUser;
    private HistoryStorage historyStorage;
    private int sessionId;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Session(HistoryStorage storage) {
        historyStorage = storage;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public boolean isSeesionExist() { return sessionUser != null; }

    public HistoryStorage getHistoryStorage() {
        return historyStorage;
    }

    public void setHistoryStorage(HistoryStorage historyStorage) {
        this.historyStorage = historyStorage;
    }

    public void storeMessage(String msg, long time) {
        if (isSeesionExist()) {
            historyStorage.addMessage(msg, time);
        }
    }
}
