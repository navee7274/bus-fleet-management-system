package com.busfleetmanagement.system.exception.auth;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException(String message){
        super(message);
    }
}
