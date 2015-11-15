package ru.mipt.session;

import ru.mipt.message.ReturnCode;

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


    public Session getSessionById(Long id) {
        if (id == ReturnCode.NO_CURRENT_SESSION)
            return null;
        return sessions.get(id);
    }

    public void addSession(Session session, long sessionId){
        sessions.put(sessionId, session);
    }

    public Long getSessionIdByUserId(Long userId) {
        return userToSessionMatch.get(userId);
    }

}
