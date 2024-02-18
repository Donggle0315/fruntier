package com.fruntier.fruntier.message;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.message.dto.LoadChatUserInfo;
import com.fruntier.fruntier.message.dto.SentMessage;
import com.fruntier.fruntier.user.controller.UserController;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    JwtTokenService jwtTokenService;
    UserInfoService userInfoService;
    MessageService messageService;

    @Autowired
    public MessageController(JwtTokenService jwtTokenService, UserInfoService userInfoService, MessageService messageService) {
        this.jwtTokenService = jwtTokenService;
        this.userInfoService = userInfoService;
        this.messageService = messageService;
    }

    @GetMapping("/conversationList")
    @RequireTokenValidation
    public String messagePage(HttpServletRequest request, Model model) throws UserNotFoundException{
        User loginUser = userInfoService.findUserWithId(((User)request.getAttribute("validatedUser")).getId());

        List<User> userList = userInfoService.findUsers();

        userList.removeIf(user -> user.getId().equals(loginUser.getId()));

        model.addAttribute("userList",userList);
        model.addAttribute("loginUser",loginUser);

        return "message";
    }
    @PostMapping("/loadChatWith")
    @RequireTokenValidation
    public ResponseEntity<?> loadChatWith(HttpServletRequest request, @RequestBody LoadChatUserInfo loadChatUserInfo) throws UserNotFoundException{
        User currentUser = userInfoService.findUserWithId(((User)request.getAttribute("validatedUser")).getId());


        String loginUsername = loadChatUserInfo.getLoginUsername();
        String opponentUsername = loadChatUserInfo.getOpponentUsername();
        if(!loginUsername.equals(currentUser.getUsername())){
            throw new UserNotFoundException("Not Logged In User");
        }
        List<Message> messageList =  messageService.fetchConversation(loginUsername,opponentUsername);
        for (Message message : messageList) {
            logger.debug("Message : "+message.getContent());
        }
        return ResponseEntity.ok(messageList);

    }

    @PostMapping("sendMessage")
    @RequireTokenValidation
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody SentMessage sentMessage) throws  UserNotFoundException {

        User currentUser = userInfoService.findUserWithId(((User) request.getAttribute("validatedUser")).getId());
        String opponentUsername = sentMessage.getOpponentUsername();
        String content = sentMessage.getContent();
        logger.debug("received Username : " + opponentUsername + "received Content : " + content);

        Message msg = messageService.sendMessage(currentUser.getUsername(), opponentUsername, content);

        logger.debug("Message : " + msg.getContent());

        return ResponseEntity.ok(msg);

    }
}

