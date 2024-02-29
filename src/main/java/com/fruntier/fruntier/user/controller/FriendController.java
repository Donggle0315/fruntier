package com.fruntier.fruntier.user.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.user.domain.FriendSearchDTO;
import com.fruntier.fruntier.user.domain.Friendship;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.user.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/friend")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    @RequireTokenValidation
    public String friendPage(
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getAttribute("validatedUser");
        List<Friendship> friendshipList = user.getFriendshipList();
        model.addAttribute("friendshipList", friendshipList);

        return "friend/friend-main";
    }

    @ResponseBody
    @GetMapping("/search")
    @RequireTokenValidation
    public List<FriendSearchDTO> searchFriend(
            HttpServletRequest request,
            @RequestParam(name = "key", required = false, defaultValue = "") String key
    ) {
        User user = (User) request.getAttribute("validatedUser");
        return friendService.searchUser(user, key);
    }

    @ResponseBody
    @GetMapping("/request/sent")
    @RequireTokenValidation
    public List<FriendSearchDTO> getFriendRequestSentList(
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        return friendService.getFriendRequestSentList(user);
    }

    @ResponseBody
    @GetMapping("/request/incoming")
    @RequireTokenValidation
    public List<FriendSearchDTO> getFriendRequestIncomingList(
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        return friendService.getFriendRequestIncomingList(user);
    }

    @ResponseBody
    @PostMapping("/request")
    @RequireTokenValidation
    public void requestFriend(
            @RequestBody String requestString,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        Long requestUserId = Long.parseLong(requestString);
        friendService.requestFriend(user, requestUserId);
    }

    @ResponseBody
    @PostMapping("/request/accept")
    public void requestAccept(
            @RequestBody String acceptUserIdString,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        Long acceptUserId = Long.parseLong(acceptUserIdString);
        friendService.acceptRequestFriend(user, acceptUserId);
    }


    @ResponseBody
    @PostMapping("/request/decline")
    public void requestDecline(
            @RequestBody String cancelUserIdString,
            HttpServletRequest request
    ) {
        User user = (User) request.getAttribute("validatedUser");
        Long cancelUserId = Long.parseLong(cancelUserIdString);
        friendService.cancelRequestFriend(user, cancelUserId);
    }
}
