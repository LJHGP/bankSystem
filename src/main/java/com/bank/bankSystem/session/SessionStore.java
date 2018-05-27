package com.bank.bankSystem.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionStore {
    private static SessionStore instance = new SessionStore();
    private Map<String, HttpSession> userSessionMap;

    private SessionStore() {
        userSessionMap = new HashMap<>();
    }

    public static SessionStore getInstance() {
        return instance;
    }

    public HttpSession getSession(String id) {
        return userSessionMap.get(id);
    }

    public void addUser(String id, HttpSession session) {
        userSessionMap.put(id, session);
    }

    public void removeUser(String id) {
        userSessionMap.remove(id);
    }
}
