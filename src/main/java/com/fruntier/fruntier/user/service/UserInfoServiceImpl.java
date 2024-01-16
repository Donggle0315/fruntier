package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;

import java.util.Optional;

public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public Boolean joinUser(User user) {

        return true;
    }

    @Override
    public Optional<User> findUserWithId(Long userId) {
        return Optional.empty();
    }

    @Override
    public Boolean modifyUser(User user) {
        return null;
    }

    @Override
    public Boolean withdrawUser(User user) {
        return null;
    }
}
