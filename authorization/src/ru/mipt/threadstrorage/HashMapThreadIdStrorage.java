package ru.mipt.threadstrorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artem on 15.11.15.
 */
public class HashMapThreadIdStrorage implements ThreadsIdStorage {
    private HashMap<Long,Long> threadIdStrorage;

    public HashMapThreadIdStrorage() {
        this.threadIdStrorage = new HashMap<Long, Long>();
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
