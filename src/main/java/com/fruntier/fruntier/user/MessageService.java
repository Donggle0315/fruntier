package com.fruntier.fruntier.user;

import java.util.List;
import java.util.Map;

public interface MessageService {
    /* Internal Message Service */
    /**
     * 메세지 전달 기능
     * @param receiver 메세지를 받을 유저 클래스
     * @param message 전송할 메세지 오브젝트
     * @return 메세지 전송 성공 여부
     */
    boolean sendMessage(User receiver, Object message);

    /**
     * 메세지 확인 기능
     * @return 받은 메세지를 <발송인, List<메시지>> 형태로 맵으로 저장
     */
    Map<User, List<Object>> takeMessage();

    /* E-mail Service */

    /**
     * 이메일 발송 기능
     * @param receiver 이메일을 받을 유저 클래스
     * @param message 전송할 메세지
     * @return 이메일 발송 성공 여부
     */
    boolean sendEmail(User receiver, Object message);
}
