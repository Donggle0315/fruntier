package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    /**
     * 회원가입 기능 - 중복 회원가입 불가
     * @param user 회원가입 유저 클래스
     */
    Boolean joinUser(User user);

    /**
     * 유저 검색 기능
     * @param userId 찾고자 하는 유저의 아이디(PK)
     * @return User class
     */
    User findUserWithId(Long userId) throws UserNotFoundException;

    /**
     * 유저 정보 수정 기능
     * @param user 수정할 정보를 담은 유저 클래스
     * @return 수정 성공 여부
     */
    Boolean modifyUser(User user);

    /**
     * 유저 탈퇴 기능
     * @param user 탈퇴할 유저 아이디
     * @return 탈퇴 성공 여부
     */
    Boolean withdrawUser(User user);

    /**
     * 임시로 만듬. 수정 예정 --> 원래는 친구들만 리턴하는데, 임시로 모두 리턴하도록.
     * @return List of all users
     */
    List<User> findUsers();
}
