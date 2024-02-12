package com.fruntier.fruntier.message;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.controller.UserController;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.JsonEmptyException;
import com.fruntier.fruntier.user.exceptions.NoTokenException;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.service.UserInfoService;
import com.fruntier.fruntier.user.service.UserInfoServiceImpl;
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
import java.util.Optional;

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
    public String messagePage(@CookieValue(value = "authToken", required = false) String token, Model model) {

        try {

            if (token != null) {
                User loginUser = userInfoService.findUserWithId(jwtTokenService.validateTokenReturnUser(token).getId());
                List<User> userList = userInfoService.findUsers();

                userList.removeIf(user -> user.getId().equals(loginUser.getId()));

                model.addAttribute("userList",userList);
                model.addAttribute("loginUser",loginUser);

                return "message";
            } else {
                // No token found in cookies user is not logged in.
                logger.error("No token found in cookies");
                return "redirect:login";
            }

        } catch (TokenValidationException e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return "/user/login";
        } catch (UserNotFoundException e) {
            logger.error("User not Found Error : {}", e.getMessage(), e);
            return "redirect:logout";
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return "/user/login";
        }
    }
    @PostMapping("/loadChatWith")
    public ResponseEntity<?> loadChatWith(@CookieValue(value = "authToken", required = true) String token, @RequestBody Map<String, String> userInfo) {
        try {
            Long userId;


            //get user information from token.
            User currentUser = jwtTokenService.validateTokenReturnUser(token);
            userId = currentUser.getId();
            User obtainedUser = userInfoService.findUserWithId(userId);

            String loginUsername =  userInfo.get("loginUsername");
            String opponentUsername = userInfo.get("opponentUsername");

            List<Message> messageList =  messageService.fetchConversation(loginUsername,opponentUsername);

            for (Message message : messageList) {
                logger.debug("Message : "+message.getContent());
            }
            return ResponseEntity.ok(messageList);

        } catch (TokenValidationException e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            logger.error("Unexpected Error regarding Token: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("sendMessage")
    public ResponseEntity<?> sendMessage(@CookieValue(value = "authToken", required = true) String token, @RequestBody Map<String, String> opponentInfo) {
        try {
            logger.info("entered sendMessage");



            //get user information from token.
            User currentUser = jwtTokenService.validateTokenReturnUser(token);
            Long userId = currentUser.getId();
            String username = currentUser.getUsername();


            User obtainedUser = userInfoService.findUserWithId(userId);



            String opponentUsername = opponentInfo.get("opponentUsername");
            String content = opponentInfo.get("content");
            logger.debug("received Username : "+opponentUsername + "received Content : "+content);

            Message sentMessage =  messageService.sendMessage(username,opponentUsername,content);

            logger.debug("Message : " + sentMessage.getContent());

            return ResponseEntity.ok(sentMessage);

        } catch (TokenValidationException e) {
            logger.error("Token validation error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            logger.error("Unexpected Error regarding Token: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

