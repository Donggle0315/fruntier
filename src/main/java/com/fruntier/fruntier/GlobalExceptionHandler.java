package com.fruntier.fruntier;

import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<String> handleUserNotLoggedInException(UserNotLoggedInException userNotLoggedInException) {
        return new ResponseEntity<>("Not logged in!", HttpStatus.UNAUTHORIZED);
    }
}
