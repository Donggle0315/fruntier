package com.fruntier.fruntier.user;

import java.util.List;
import java.util.Map;

public interface FriendService {
    /**
     * 친구 맺기 기능 - 양쪽 친구목록에 서로를 추가
     * @param f1 친구1
     * @param f2 친구2
     * @return 성공 여부
     */
    boolean makeFriendship(User f1, User f2);

    /**
     * 친구 끊기 기능 - 양쪽 친구목록에서 서로를 삭제
     * @param f1 친구1
     * @param f2 친구2
     * @return 성공여부
     */
    boolean breakFriendship(User f1, User f2);

    /**
     * 친구 찾기 기능
     * @param f 찾고자 하는 친구 정보
     * @return 찾고자 하는 친구 클래스 반환
     */
    User findFriend(User f);

}
