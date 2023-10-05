package com.fruntier.fruntier.user;

public interface UserRepository {
    /**
     * 유저를 저장소에 저장
     * @param user 저장하고자 하는 유저 클래스
     */
    void save(User user);

    /**
     * 유저를 저장소에서 검색
     * @param userId 검색하고자 하는 유저의 아이디
     * @return 유저 클래스
     */
    User findUser(Long userId);

    /**
     * 유저를 저장소에서 삭제
     * @param userId 삭제할 유저 아이디
     */
    void deleteUser(Long userId);
}
