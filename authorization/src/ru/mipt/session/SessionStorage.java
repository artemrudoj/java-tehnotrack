package ru.mipt.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by artem on 29.10.15.
 */
public class SessionStorage {
    // I suppose, that the most popular usage of this is geting element by id . ~o(1)
    Map<Long, Session> sessions = new HashMap<>();
    Map<Long, Long> userToSessionMatch = new HashMap<>();
    AtomicLong atomicLong = new AtomicLong(0);


    public Session getSessionById(Long id) {
        return sessions.get(id);
    }

    public Long addSession(Session session){
        Long newId = atomicLong.getAndIncrement();
        sessions.put(newId, session);
        return newId;
    }

    public void createUserToSessionMatch(Long userId, Long sessionId) {
        userToSessionMatch.put( userId, sessionId);
    }

    public Long getSessionIdByUserId(Long userId) {
        return userToSessionMatch.get(userId);
    }

}
