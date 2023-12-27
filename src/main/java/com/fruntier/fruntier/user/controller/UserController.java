package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserJoinLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    UserJoinLoginService userJoinLoginService;

    public UserController(UserJoinLoginService userJoinLoginService) {
        this.userJoinLoginService = userJoinLoginService;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response){

        try {
            User user = userJoinLoginService.loginUser(username,password);
        }catch (UserNotFoundException | PasswordWrongException exception){

        }
        return null;

    }
}
