package com.fruntier.fruntier.message.controller;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.message.service.MessageService;
import com.fruntier.fruntier.message.domain.Message;
import com.fruntier.fruntier.message.dto.ChatUserInfoDto;
import com.fruntier.fruntier.message.dto.SentMessageDto;
import com.fruntier.fruntier.user.controller.UserController;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/message")
@Slf4j
public class MessageController {
    UserInfoService userInfoService;
    MessageService messageService;

    @Autowired
    public MessageController(UserInfoService userInfoService, MessageService messageService) {

        this.userInfoService = userInfoService;
        this.messageService = messageService;
    }

    @GetMapping
    @RequireTokenValidation
    public String messagePage(HttpServletRequest request, Model model) {
        User loginUser = ((User) request.getAttribute("validatedUser"));

        List<User> userList = userInfoService.findUsers(); //todo : friend 구현
        userList.removeIf(user -> user.getId().equals(loginUser.getId()));

        model.addAttribute("userList", userList);
        model.addAttribute("loginUser", loginUser);

        return "message";
    }

    @GetMapping("/chat")
    @RequireTokenValidation
    public ResponseEntity<?> loadChatWith(
            HttpServletRequest request,
            @RequestParam String opponentUsername
    ){

        User currentUser = (User) request.getAttribute("validatedUser");
        String currentUsername = currentUser.getUsername();

        List<Message> messageList = messageService.fetchConversation(currentUsername, opponentUsername);
        for (Message message : messageList) {
            log.debug("Message : " + message.getContent());
        }
        return ResponseEntity.ok(messageList);

    }

    @PostMapping("chat")
    @RequireTokenValidation
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @RequestBody SentMessageDto sentMessageDto) throws UserNotFoundException {

        User currentUser = (User) request.getAttribute("validatedUser");
        String opponentUsername = sentMessageDto.getOpponentUsername();
        String content = sentMessageDto.getContent();
        log.debug("received Username : {}, received Content : {}",opponentUsername,content);

        Message msg = messageService.sendMessage(currentUser.getUsername(), opponentUsername, content);

        log.debug("Message : {}",msg.getContent());

        return ResponseEntity.ok(msg);

    }
}

