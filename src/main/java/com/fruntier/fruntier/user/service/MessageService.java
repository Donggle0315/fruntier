package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.Message;
import com.fruntier.fruntier.user.domain.User;

import java.util.List;

public interface MessageService {
    /* Internal Message Service */
    /**
     * 메세지 전달 기능
     * @param message 전송할 메세지 오브젝트
     * @return 메세지 전송 성공 여부
     */
    Boolean sendMessage(Message message);

    /**
     * 메세지 확인 기능
     * @param user 메세지를 확인할 유저
     * @return 받은 메세지를 <발송인, List<메시지>> 형태로 맵으로 저장
     */
    List<Message> receiveMessage(User user);

    /* E-mail Service */

    /**
     * 이메일 발송 기능
     * @param receiver 이메일을 받을 유저 클래스
     * @param message 전송할 메세지
     * @return 이메일 발송 성공 여부
     */
//    Boolean sendEmail(User receiver, Object message);
}
