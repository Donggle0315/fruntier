package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.user.domain.Position;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.HasDuplicateUsernameException;
import com.fruntier.fruntier.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserJoinLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserJoinLoginService userJoinLoginService;
    private JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserJoinLoginService userJoinLoginService, JwtTokenService jwtTokenService){
        this.userJoinLoginService = userJoinLoginService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/join")
    public String userJoinPage() {
        return "join";
    }

    @ResponseBody
    @PostMapping("/join")
    public String userJoin(@RequestBody Map<String, String> joinData) {
        if (joinData.containsValue(null)){
            return "error";
        }
        System.out.println("joinData = " + joinData);
        String username = joinData.get("username");
        String password = joinData.get("password");
        String email = joinData.get("email");
        String name = joinData.get("name");
        String address = joinData.get("address");
        String sex = joinData.get("male");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setPosition(Position.USER);
        user.setAddress(address);
        if (sex.equals("male")){
            user.setMale(true);
        }
        else {
            user.setMale(false);
        }

        try {
            userJoinLoginService.joinUser(user);
        }
        catch (HasDuplicateUsernameException he) {
            System.out.println("HasDuplicateNameException");
            return "error";
        }

        return "ok";
    }


    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response, Model model){

        try {
            User user = userJoinLoginService.loginUser(username,password);
            String token = jwtTokenService.generateToken(user);

            response.setHeader("Authorization", "Bearer " + token);
            return "redirect:/home";
        }catch (UserNotFoundException | PasswordWrongException exception){
            if(exception instanceof UserNotFoundException){
                model.addAttribute("errorMessage","User not Found!");
            }else{
                model.addAttribute("errorMessage","Wrong password");
            }
            return "/login";


        }
    }
}
