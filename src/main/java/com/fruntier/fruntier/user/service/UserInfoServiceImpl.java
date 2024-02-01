package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User findUserWithId(Long userId) throws UserNotFoundException{
        //find the user with id.
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){throw new UserNotFoundException("User not found in FindUserWithId");}

        return user.get();
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

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
