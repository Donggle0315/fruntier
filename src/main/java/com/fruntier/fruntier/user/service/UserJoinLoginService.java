package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

public interface UserJoinLoginService {
    /**
     *
     * @return 성공시 유저 객체, 실패시 null
     */
    User loginUser(String username, String password) throws UserNotFoundException, PasswordWrongException;
}
