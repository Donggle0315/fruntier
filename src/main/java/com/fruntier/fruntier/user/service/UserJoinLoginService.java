package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.HasDuplicateUsernameException;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import jakarta.servlet.http.Cookie;

public interface UserJoinLoginService {
    /**
     *
     * @return 성공시 유저 객체, 실패시 null
     */
    User checkUsernameAndPassword(String username, String password) throws UserNotFoundException;


    User joinUser(User user) throws HasDuplicateUsernameException;

    boolean isDuplicateUsername(String username);

    Cookie tokenToCookie(String token);
}

