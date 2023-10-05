package com.fruntier.fruntier.user;

public interface UserInfoService {
    /**
     * 회원가입 기능 - 중복 회원가입 불가
     * @param user 회원가입 유저 클래스
     */
    void join(User user);

    /**
     * 유저 검색 기능
     * @param userId 찾고자 하는 유저의 아이디(PK)
     * @return User class
     */
    User findUser(Long userId);

    /**
     * 유저 정보 수정 기능
     * @param userId 수정하고자 하는 유저 아이디
     * @param user 수정할 정보를 담은 유저 클래스
     * @return 수정 성공 여부
     */
    boolean modifyUserInfo(Long userId, User user);

    /**
     * 유저 탈퇴 기능
     * @param userId 탈퇴할 유저 아이디
     * @return 탈퇴 성공 여부
     */
    boolean withdraw(Long userId);
}
