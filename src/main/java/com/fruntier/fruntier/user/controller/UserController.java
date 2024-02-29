package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.domain.JoinDataDTO;
import com.fruntier.fruntier.user.domain.Position;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.domain.UserInfoDTO;
import com.fruntier.fruntier.user.exceptions.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.fruntier.fruntier.user.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.service.UserJoinLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserInfoService userInfoService;
    private final UserJoinLoginService userJoinLoginService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/join")
    public String userJoinPage() {
        return "join";
    }

    @ResponseBody
    @GetMapping("/join/{username}/duplicate")
    public Boolean userCheckDuplicateUsername(@PathVariable("username") String username) {
        return userJoinLoginService.isDuplicateUsername(username);

    }

    @GetMapping("/info")
    @RequireTokenValidation
    public String userInfo(HttpServletRequest request, Model model) {

        User user = (User) request.getAttribute("validatedUser");

        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("address", user.getAddress());
        model.addAttribute("message", user.getMessage());

        return "info";
    }


    @ResponseBody
    @PostMapping("/join")
    public String userJoin(@RequestBody JoinDataDTO joinData) {
        System.out.println("joinData = " + joinData);
        if (joinData.isNull()) {
            return "error";
        }

        User user = makeNewUser(joinData);
        try {
            userJoinLoginService.joinUser(user);
        } catch (HasDuplicateUsernameException he) {
            System.out.println("HasDuplicateNameException");
            return "error";
        }

        return "ok";
    }

    private static User makeNewUser(JoinDataDTO joinData) {
        User user = new User();

        user.setUsername(joinData.getUsername());
        user.setPassword(joinData.getPassword());
        user.setEmail(joinData.getEmail());
        user.setName(joinData.getName());
        user.setPosition(Position.USER);
        user.setAddress(joinData.getAddress());
        user.setIsMale(joinData.isMale());

        return user;
    }


    @GetMapping("/login")
    public String showLoginForm(@CookieValue(value = "authToken", required = false) String token) {
        if (token != null) {
            return "redirect:/user/home";
        }

        return "login";
    }

    @GetMapping("/home")
    @RequireTokenValidation
    public String goHomePage(Model model, HttpServletRequest request) {

        User user = (User) request.getAttribute("validatedUser");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("id", user.getId());

        return "home";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response, Model model) throws UserNotFoundException {

        User user = userJoinLoginService.checkUsernameAndPassword(username, password);
        String token = jwtTokenService.generateToken(user);
        response.addCookie(userJoinLoginService.tokenToCookie(token));
        model.addAttribute("username", username);
        return "redirect:home";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         @CookieValue(value = "authToken", required = false) Cookie cookie) {

        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "redirect:login";
    }

    @PostMapping("/info")
    @RequireTokenValidation
    public ResponseEntity<?> submitUserInfo(HttpServletRequest request,
                                            @RequestBody UserInfoDTO userInfo) throws UserNotFoundException {
        User user = (User) request.getAttribute("validatedUser");
        User obtainedUser = userInfoService.findUserById(user.getId());

        obtainedUser.setName(userInfo.getName());
        obtainedUser.setEmail(userInfo.getEmail());
        obtainedUser.setAddress(userInfo.getAddress());
        obtainedUser.setMessage(userInfo.getMessage());

        userInfoService.modifyUser(obtainedUser);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
