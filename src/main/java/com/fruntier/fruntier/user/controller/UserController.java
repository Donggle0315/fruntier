package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}
