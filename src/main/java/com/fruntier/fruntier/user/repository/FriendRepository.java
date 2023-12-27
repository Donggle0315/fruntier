package com.fruntier.fruntier.user.repository;

import com.fruntier.fruntier.user.domain.User;

import java.util.List;

public interface FriendRepository {

    /**
     * 저장소에 친구관계 저장
     * @param userId1 친구1의 userid
     * @param userId2 친구2의 userid
     */
    Boolean saveFriend(Long userId1, Long userId2);

    /**
     * 저장소에 친구관계 삭제
     * @param userId1 친구1의 userid
     * @param userId2 친구2의 userid
     */
    Boolean deleteFriend(Long userId1, Long userId2);

    /**
     * 유저 객체의 친구 리스트 반환
     * @param user
     * @return user의 친구 리스트
     */
    List<Long> getFriends(User user);

    /**
     *
     * @param userId1 친구 1의 userid
     * @param userId2 친구 2의 userid
     * @return 친구 목록에 이미 존재하는지 여부
     */
    Boolean checkIsFriend(Long userId1, Long userId2);

}
