package com.fruntier.fruntier.user.controller;

import com.fruntier.fruntier.user.domain.Position;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.HasDuplicateUsernameException;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.fruntier.fruntier.user.service.UserInfoService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.fruntier.fruntier.user.service.UserInfoService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserJoinLoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserJoinLoginService userJoinLoginService;
    private JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserJoinLoginService userJoinLoginService, JwtTokenService jwtTokenService) {
        this.userJoinLoginService = userJoinLoginService;
        this.jwtTokenService = jwtTokenService;
    }

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
    public String userInfo() {
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
            user.setMale(true);
        } else {
            user.setMale(false);
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
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String goHomePage(HttpServletRequest request, Model model) {
        try {
            Cookie[] cookies = request.getCookies();
            String token = Arrays.stream(cookies)
                    .filter(c -> "authToken".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            if (token != null) {
                User user = jwtTokenService.validateTokenReturnUser(token);
                model.addAttribute("username", user.getUsername());
                model.addAttribute("id",user.getId());
                logger.info("Login successful) username: "+user.getUsername()+"id: "+user.getId());
                return "home";
            } else {
                // No token found in cookies user is not logged in.
                logger.error("No token found in cookies");
                return "redirect:login";
            }
        } catch (TokenValidationException e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return "redirect:login";
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return "redirect:login";
        }
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
        // Redirect to login page or another appropriate page after logout
        return "redirect:login";
    }
}
