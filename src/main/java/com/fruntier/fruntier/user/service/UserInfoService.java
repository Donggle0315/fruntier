package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.globalexception.UserNotFoundException;

import java.util.List;

public interface UserInfoService {


    /**
     * 유저 검색 기능
     * @param userId 찾고자 하는 유저의 아이디(PK)
     * @return User class
     */
    User findUserById(Long userId) throws UserNotFoundException;

    /**
     * 유저 정보 수정 기능
     * @param user 수정할 정보를 담은 유저 클래스
     * @return 수정 성공 여부
     */
    Boolean modifyUser(User user);
    

    /**
     * 임시로 만듬. 수정 예정 --> 원래는 친구들만 리턴하는데, 임시로 모두 리턴하도록.
     * @return List of all users
     */
    List<User> findUsers();
}
