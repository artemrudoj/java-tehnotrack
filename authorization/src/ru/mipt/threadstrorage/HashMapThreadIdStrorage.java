package ru.mipt.threadstrorage;

import java.util.HashMap;


public class HashMapThreadIdStrorage implements ThreadsIdStorage {
    private HashMap<Long,Long> threadIdStrorage;

    public HashMapThreadIdStrorage() {
        this.threadIdStrorage = new HashMap<>();
    }

    @Override
    public long getSessionId(long threadId) {
        return threadIdStrorage.get(threadId);
    }

    @Override
    public void add(long threadId, long SessionId) {
        threadIdStrorage.put(threadId, SessionId);
    }


}
