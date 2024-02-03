package com.fruntier.fruntier.globalexception;

public class UserNotLoggedInException extends Exception{
    public UserNotLoggedInException(String errorMessage) {
        super(errorMessage);
    }
}
