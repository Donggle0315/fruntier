package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.user.domain.User;
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
    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }

    @GetMapping("/join")
    public String userJoinPage() {
        return "join";
    }

    @ResponseBody
    @PostMapping("/join")
    public String userJoin(@RequestBody Map<String, String> joinData) {
        if (joinData.containsValue(null)){
            // throw exception
        }
        System.out.println("joinData = " + joinData);
        String username = joinData.get("username");
        String password = joinData.get("password");
        String email = joinData.get("email");
        String name = joinData.get("name");
        String address = joinData.get("address");
        String sex = joinData.get("male");

        User user = new User();

        return "ok";
    }
  
    UserJoinLoginService userJoinLoginService;
    JwtTokenService jwtTokenService;

    public UserController(UserJoinLoginService userJoinLoginService) {
        this.userJoinLoginService = userJoinLoginService;
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
