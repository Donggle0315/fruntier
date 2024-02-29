package com.fruntier.fruntier.user.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.user.domain.FriendSearchDTO;
import com.fruntier.fruntier.user.domain.Friendship;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.user.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  TODO :
 *  이미 friend => search 안되게 하기
 *  거절 기능
 *  자기 자신이 친구 리스트 이름에 들어감??
 *  sidebar?
  */

@Controller
@RequestMapping("/user/friend")
public class FriendController {

    private final JwtTokenService jwtTokenService;
    private final FriendService friendService;

    @Autowired
    public FriendController(JwtTokenService jwtTokenService, FriendService friendService) {
        this.jwtTokenService = jwtTokenService;
        this.friendService = friendService;
    }

    @GetMapping
    public String friendPage(
            @CookieValue(value = "authToken", required = false) String authCookie,
            Model model
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            List<Friendship> friendshipList = user.getFriendshipList();
            model.addAttribute("friendshipList", friendshipList);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
        return "friend/friend-main";
    }

    @ResponseBody
    @GetMapping("/search")
    public List<FriendSearchDTO> searchFriend(
            @RequestParam(name="key", required = false, defaultValue = "") String key,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            return friendService.searchUser(user, key);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/request/sent")
    public List<FriendSearchDTO> getFriendRequestSentList(
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            return friendService.getFriendRequestSentList(user);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/request/incoming")
    public List<FriendSearchDTO> getFriendRequestIncomingList(
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            return friendService.getFriendRequestIncomingList(user);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/request")
    public void requestFriend(
            @RequestBody String requestString,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            Long requestUserId = Long.parseLong(requestString);
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            friendService.requestFriend(user, requestUserId);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/request/accept")
    public void requestAccept(
            @RequestBody String acceptUserIdString,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            Long acceptUserId = Long.parseLong(acceptUserIdString);
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            friendService.acceptRequestFriend(user, acceptUserId);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }


    @ResponseBody
    @PostMapping("/request/decline")
    public void requestDecline(
            @RequestBody String cancelUserIdString,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            Long cancelUserId = Long.parseLong(cancelUserIdString);
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            friendService.cancelRequestFriend(user, cancelUserId);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
    }
}
