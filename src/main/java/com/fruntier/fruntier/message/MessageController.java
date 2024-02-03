package com.fruntier.fruntier.message;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.controller.UserController;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserInfoService;
import com.fruntier.fruntier.user.service.UserInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    JwtTokenService jwtTokenService;
    UserInfoService userInfoService;

    public MessageController(JwtTokenService jwtTokenService, UserInfoService userInfoService) {
        this.jwtTokenService = jwtTokenService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/conversationList")
    public String messagePage(@CookieValue(value = "authToken", required = false) String token, Model model) {

        try {

            if (token != null) {
                User loginUser = userInfoService.findUserWithId(jwtTokenService.validateTokenReturnUser(token).getId());
                List<User> userList = userInfoService.findUsers();

                model.addAttribute("userList",userList);
                model.addAttribute("loginUser",loginUser);

                return "message";
            } else {
                // No token found in cookies user is not logged in.
                logger.error("No token found in cookies");
                return "redirect:login";
            }

        } catch (TokenValidationException e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return "redirect:login";
        } catch (UserNotFoundException e) {
            logger.error("User not Found Error : {}", e.getMessage(), e);
            return "redirect:logout";
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return "redirect:login";
        }
    }
}
