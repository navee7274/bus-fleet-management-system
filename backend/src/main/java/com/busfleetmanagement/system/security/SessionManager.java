package com.busfleetmanagement.system.security;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

@Component
public class SessionManager {

    private final Map<String, Integer> sessions = new HashMap<>();

    public String createSession(Integer ownerId) {
        String sessionId = UUID.randomUUID().toString();

        sessions.put(sessionId, ownerId);

        return sessionId;
    }

    public Integer getOwnerId(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}