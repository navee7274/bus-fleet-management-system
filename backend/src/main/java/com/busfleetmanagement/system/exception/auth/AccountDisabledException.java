package com.busfleetmanagement.system.exception.auth;

public class AccountDisabledException extends RuntimeException{
    public AccountDisabledException(String message){
        super(message);
    }
}
