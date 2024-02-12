package com.fruntier.fruntier.message;

import com.fruntier.fruntier.user.exceptions.UserNotFoundException;

import java.util.List;

public interface MessageService {
    /* Internal Message Service */
    /**
     * 메세지 전달 기능
     * @param senderUsername : 메시지 보내는 사람의 ID
     * @param receiverUsername : 메시지 받는 사람의 ID
     * @param content : content of the message
     */
    Message sendMessage(String senderUsername, String receiverUsername, String content) throws UserNotFoundException;


    /**
     *
     * @param loginUserUsername : username of the current user
     * @param opponentUsername : username of the user chatting with the currently logged in user
     * @return : list of all the messages of the conversation between two users
     */
    List<Message> fetchConversation(String loginUserUsername, String opponentUsername);

    /* E-mail Service */

    /**
     * 이메일 발송 기능
     * @param receiver 이메일을 받을 유저 클래스
     * @param message 전송할 메세지
     * @return 이메일 발송 성공 여부
     */
//    Boolean sendEmail(User receiver, Object message);
}
