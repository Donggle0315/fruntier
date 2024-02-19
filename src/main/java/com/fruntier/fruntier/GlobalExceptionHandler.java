package com.fruntier.fruntier;

import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

        return "redirect:/user/logout";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException userNotFoundException, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("not_logged_in",true);
        logger.info("User Not Found Exception");

        return "redirect:/user/logout";
    }

    @ExceptionHandler(TokenValidationException.class)
    public String handleTokenValidationException(TokenValidationException tokenValidationException, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("not_logged_in", true);
        logger.info("Token is not Valid");

        return "redirect:/user/logout";
    }
}
