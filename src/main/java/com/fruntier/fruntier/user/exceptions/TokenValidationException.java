package com.fruntier.fruntier.user.exceptions;

public class TokenValidationException extends Exception{
    public TokenValidationException(String message,Exception e) {
        super(message);
    }
}
