package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepository userRepository;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException{
        return userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException("User not found in FindUserWithId")
        );

    }

    @Override
    public Boolean modifyUser(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
