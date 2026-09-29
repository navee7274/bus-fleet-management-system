package com.busfleetmanagement.system.dto;

public class LoginResponse {

    private String message;
    private Integer ownerId;
    private String username;
    private String name;
    private String sessionId;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            Integer ownerId,
            String username,
            String name,
            String sessionId
    ) {
        this.message = message;
        this.ownerId = ownerId;
        this.username = username;
        this.name = name;
        this.sessionId = sessionId;
    }

    public LoginResponse(
            String message,
            Integer ownerId,
            String username,
            String name
    ) {
        this.message = message;
        this.ownerId = ownerId;
        this.username = username;
        this.name = name;
        this.sessionId = null;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMessage() {
        return message;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}