package com.fruntier.fruntier;

import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger("GlobalException");

    @ExceptionHandler(UserNotLoggedInException.class)
    public String handleUserNotLoggedInException(
            UserNotLoggedInException userNotLoggedInException,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addAttribute("not_logged_in", true);
        logger.info("Not Logged in Exception");

        return "redirect:/user/login";
    }
}
