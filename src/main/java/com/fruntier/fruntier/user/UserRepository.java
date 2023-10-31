package com.fruntier.fruntier.user;

import java.util.Optional;

public interface UserRepository {
    /**
     * 유저를 저장소에 저장
     * @param user 저장하고자 하는 유저 클래스
     * @return 가입에 성공한 유저의 userid
     */
    Long createUser(User user);

    /**
     * 유저를 저장소에서 검색
     * @param userId 검색하고자 하는 유저의 아이디
     * @return 유저 클래스
     */
    Optional<User> findUser(Long userId);

    /**
     * 유저를 업데이트
     * @param user
     * @return 성공 실패 여부
     */
    Boolean updateUser(User user);

    /**
     * 유저를 저장소에서 삭제
     * @param userId 삭제할 유저 아이디
     * @return 성공 실패 여부
     */
    Boolean deleteUser(Long userId);



}
