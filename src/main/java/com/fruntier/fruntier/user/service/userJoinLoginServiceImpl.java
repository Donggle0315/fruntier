package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.HasDuplicateUsernameException;
import com.fruntier.fruntier.user.exceptions.PasswordWrongException;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userJoinLoginServiceImpl implements UserJoinLoginService {
    UserRepository userRepository;

    @Autowired
    public userJoinLoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loginUser(String username, String password) throws UserNotFoundException, PasswordWrongException{
        User idUser;
        User pwUser;
        Optional<User> idCheckUser = userRepository.findByUsername(username);
        Optional<User> pwCheckUser = userRepository.findByUsernameAndPassword(username,password);


        if(idCheckUser.isEmpty()){ // username not found
            throw new UserNotFoundException("user(username) is not found");
        }

        if(pwCheckUser.isEmpty()){
            throw new PasswordWrongException("wrong password");
        }

        return pwCheckUser.get();
    }

    @Override
    public User joinUser(User user) throws HasDuplicateUsernameException {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new HasDuplicateUsernameException("Duplicate Username Found");
        }
        User new_user = userRepository.save(user);
        return new_user;
    }

    @Override
    public boolean isDuplicateUsername(String username){
        return userRepository.findByUsername(username).isPresent();
    }

}
