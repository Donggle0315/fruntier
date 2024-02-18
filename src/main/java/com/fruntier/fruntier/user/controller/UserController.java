package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.TokenValidationAspect;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.domain.Position;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.fruntier.fruntier.user.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserInfoService userInfoService;
    private final UserJoinLoginService userJoinLoginService;
    private final JwtTokenService jwtTokenService;
    private final TokenValidationAspect tokenValidationAspect;


    @GetMapping("/join")
    public String userJoinPage() {
        return "join";
    }

    @ResponseBody
    @GetMapping("/join/{username}/duplicate")
    public String userCheckDuplicateUsername(@PathVariable("username") String username) {
        if (userJoinLoginService.isDuplicateUsername(username)) {
            return "true";
        }
        return "false";
    }

    @GetMapping("/info")
    @RequireTokenValidation
    public String userInfo(HttpServletRequest request, Model model) throws UserNotFoundException{
        // Assuming the aspect has already validated the user and set it in the request,
        // and any TokenValidationException will be caught by the global exception handler.
        User user = (User) request.getAttribute("validatedUser");
        User userInfo = userInfoService.findUserWithId(user.getId());

        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("name", userInfo.getName());
        model.addAttribute("email", userInfo.getEmail());
        model.addAttribute("address", userInfo.getAddress());
        model.addAttribute("message", userInfo.getMessage());
        return "info";
    }


    @ResponseBody
    @PostMapping("/join")
    public String userJoin(@RequestBody Map<String, String> joinData) {
        System.out.println("joinData = " + joinData);
        if (joinData.containsValue(null)) {
            return "error";
        }
//        System.out.println("joinData = " + joinData);
        String username = joinData.get("username");
        String password = joinData.get("password");
        String email = joinData.get("email");
        String name = joinData.get("name");
        String address = joinData.get("address");
        String sex = joinData.get("sex");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setPosition(Position.USER);
        user.setAddress(address);
        if (sex.equals("male")) {
            user.setIsMale(true);
        } else {
            user.setIsMale(false);
        }

        try {
            userJoinLoginService.joinUser(user);
        } catch (HasDuplicateUsernameException he) {
            System.out.println("HasDuplicateNameException");
            return "error";
        }

        return "ok";
    }


    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return "login";
        }
        String token = Arrays.stream(cookies)
                .filter(c -> "authToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token != null) {
            //user is already logged in. invalidate.
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    // Set the cookie's max age to 0 to delete it
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Ensure the path matches the path of the cookie you are deleting
                    // Add the cookie to the response
                    response.addCookie(cookie);
                }
            }
        }

        return "login";
    }

    @GetMapping("/home")
    @RequireTokenValidation
    public String goHomePage(Model model, HttpServletRequest request) {

        User user = (User) request.getAttribute("validatedUser");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("id",user.getId());
        logger.info("Login successful) username: "+user.getUsername()+"id: "+user.getId());

        return "home";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response, Model model) {

        try {
            User user = userJoinLoginService.loginUser(username, password);
            String token = jwtTokenService.generateToken(user);

            // Create a cookie and set the token as its value
            Cookie tokenCookie = new Cookie("authToken", token);
            tokenCookie.setHttpOnly(true); // Make cookie inaccessible to JavaScript
            tokenCookie.setPath("/"); // The cookie is accessible to all paths
            // Optional: tokenCookie.setSecure(true); // Send the cookie only over HTTPS

            // Add the cookie to the response
            response.addCookie(tokenCookie); //for "welcome Alex!"
            model.addAttribute("username", username);
            return "redirect:home";
        } catch (UserNotFoundException | PasswordWrongException exception) {
            // existing error handling code
            model.addAttribute("loginError","Wrong username or Password");
            return "redirect:login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Find the authToken cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    // Set the cookie's max age to 0 to delete it
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Ensure the path matches the path of the cookie you are deleting
                    // Add the cookie to the response
                    response.addCookie(cookie);
                }
            }

        }
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        // Redirect to login page or another appropriate page after logout
        return "redirect:login";
    }
    @PostMapping("/submitUserInfo")
    @RequireTokenValidation
    public ResponseEntity<?> submitUserInfo(HttpServletRequest request, @RequestBody Map<String, String> userInfo) throws UserNotFoundException{
            User user = (User) request.getAttribute("validatedUser");


            User obtainedUser = userInfoService.findUserWithId(user.getId());

            String name = userInfo.get("name");
            String email = userInfo.get("email");
            String address = userInfo.get("address");
            String message = userInfo.get("message");

            obtainedUser.setName(name);
            obtainedUser.setEmail(email);
            obtainedUser.setAddress(address);
            obtainedUser.setMessage(message);

            logger.info("Message : "+message);
            userInfoService.modifyUser(obtainedUser);

            return ResponseEntity.status(HttpStatus.OK).build();

    }
}
