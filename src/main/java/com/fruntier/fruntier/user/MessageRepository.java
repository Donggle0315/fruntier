package com.fruntier.fruntier.user;

import java.util.List;

public interface MessageRepository {

    /**
        저장소에 메시지 저장
        @param message 저장할 메시지 객체
        @return 저장 성공 여부
     */
    public Boolean addMessage(Message message);

    /**
     *
     * @param userid 메시지 수신하는 userid
     * @return 받은 메시지 객체를 모은 List
     */
    public List<Message> getMessages(Long userid);


}
