package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    UserRepository userRepository;

    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean joinUser(User user) {

        return true;
    }

    @Override
    public Optional<User> findUserWithId(Long userId){
        //find the user with id.
        return userRepository.findById(userId);
    }

    @Override
    public Boolean modifyUser(User user) {
        /*
        **HOW ON EARTH AM I TO USE THIS?
         */
        userRepository.save(user);

        return true;
    }

    @Override
    public Boolean withdrawUser(User user) {
        return null;
    }
}
