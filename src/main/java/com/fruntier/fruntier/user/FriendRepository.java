package com.fruntier.fruntier.user;

import java.util.List;

public interface FriendRepository {

    /**
     * 저장소에 친구관계 저장
     * @param user1 친구1 인스턴스
     * @param user2 친구2 인스턴스
     */
    Boolean saveFriend(User user1, User user2);

    /**
     * 저장소에 친구관계 삭제
     * @param user1 친구1 인스턴스
     * @param user2 친구2 인스턴스
     */
    Boolean deleteFriend(User user1, User user2);

    /**
     * 유저 객체의 친구 리스트 반환
     * @param user
     * @return user의 친구 리스트
     */
    List<Friend> getFriends(User user);

}
