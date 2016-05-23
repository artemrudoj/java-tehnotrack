package ru.mipt.threadstrorage;

/**
 * Created by artem on 15.11.15.
 */
public interface ThreadsIdStorage {
    long getSessionId(long threadId);
    void add(long threadId, long SessionId);
}
