package com.fruntier.fruntier.running.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRequestController {

    @Autowired
    public UserRequestController() {

    }

    @GetMapping("/running")
    public String userRequest() {
        return "userRequest";
    }
}
