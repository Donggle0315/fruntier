package com.fruntier.fruntier.user;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

//    @Autowired
//    public MemoryUserRepositoryTest(MemoryUserRepository repository) {
//        this.repository = repository;
//    }

    @AfterEach
    public void afterEach(){
        repository.clearMemberStore();
    }

    @Test
    public void save(){
        User user = new User();
        user.setName("Alex");
        user.setEmail("alexlee@naver.com");

        Long userid1 = repository.createUser(user);

        User user2 = new User();
        user.setName("Liam");
        user.setEmail("liamanderson@gmail.com");

        Long userid2 = repository.createUser(user2);

        Optional<User> result = repository.findUser(userid1);

        if(result.isPresent()){
            Assertions.assertThat(user).isEqualTo(result.get());
        }else{
            Assertions.fail("User not found in repository");
        }


    }
    @Test
    public void findUser(){
        User user = new User();
        user.setName("Alex");
        user.setEmail("alexlee@naver.com");

        Long userid1 = repository.createUser(user);

        User user2 = new User();
        user.setName("Liam");
        user.setEmail("liamanderson@gmail.com");

        Long userid2 = repository.createUser(user2);

        Optional<User> result = repository.findUser(userid1);

        if(result.isPresent()){
            Assertions.assertThat(user).isEqualTo(result.get());
        }else{
            Assertions.fail("User not found in repository");
        }

    }

        @Test
    public void updateUser(){
        User user = new User();
        User newUser; //update 할 유저 객체
        user.setName("Alex");
        user.setEmail("alexlee@naver.com");

        Long userid1 = repository.createUser(user);

        Optional<User> result = repository.findUser(userid1);


        if(result.isPresent()){
            newUser = result.get();
            newUser.setName("Liam");
            newUser.setEmail("liamanderson@gmail.com");
            repository.updateUser(newUser);
        }else{
            Assertions.fail("cannot find user in repository");
            return;
        }

        Optional<User> result2 = repository.findUser(newUser.getUserId());

        if(result2.isPresent()){
            Assertions.assertThat(result2.get()).isEqualTo(newUser);
        }else{
            Assertions.fail("could not update user");
            return;
        }





    }
}
