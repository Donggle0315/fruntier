package com.fruntier.fruntier.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @GetMapping
    public String friendPage(){

        return "friend-main";
    }
}
