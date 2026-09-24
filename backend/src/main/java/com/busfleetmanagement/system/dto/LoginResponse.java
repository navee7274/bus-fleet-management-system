package com.busfleetmanagement.system.dto;

public class LoginResponse {

    private String message;
    private Integer ownerId;
    private String username;
    private String name;

    public LoginResponse(String message, Integer ownerId, String username, String name){
        this.message = message;
        this.ownerId = ownerId;
        this.username = username;
        this.name = name;
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
}
