package com.fruntier.fruntier.user.exceptions;

public class HasDuplicateUsernameException extends Exception {
    public HasDuplicateUsernameException(String message) {
        super(message);
    }
}
