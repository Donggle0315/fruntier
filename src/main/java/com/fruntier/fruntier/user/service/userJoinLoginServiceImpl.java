package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.HasDuplicateUsernameException;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
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
    public User checkUsernameAndPassword(String username, String password) throws UserNotFoundException{
        return userRepository.findByUsernameAndPassword(username,password).orElseThrow(
                ()->new UserNotFoundException("User not Found")
        );
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

    @Override
    public Cookie tokenToCookie(String token) {
        Cookie tokenCookie = new Cookie("authToken", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        return tokenCookie;
    }

}
