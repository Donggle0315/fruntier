package com.fruntier.fruntier.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class tempController {

    @GetMapping("/")
    public String home(){


        return "home";
    }

}
