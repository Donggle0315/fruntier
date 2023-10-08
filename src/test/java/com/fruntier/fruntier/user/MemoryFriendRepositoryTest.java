package com.fruntier.fruntier.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemoryFriendRepositoryTest {

    private static MemoryFriendRepository repository = new MemoryFriendRepository();
//
//    @Autowired
//    public MemoryFriendRepositoryTest(MemoryFriendRepository repository) {
//        this.repository = repository;
//    }

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void checkIsFriend(){
        User user1 = new User();
        user1.setName("Alex");
        user1.setUserId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setUserId(2L);

        Assertions.assertThat(repository.checkIsFriend(user1.getUserId(),user2.getUserId())).isEqualTo(false);

        repository.saveFriend(user1.getUserId(),user2.getUserId());

        Assertions.assertThat(repository.checkIsFriend(user1.getUserId(),user2.getUserId())).isEqualTo(true);

    }
    @Test
    public void saveFriend(){
        User user1 = new User();
        user1.setName("Alex");
        user1.setUserId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setUserId(2L);

        User user3 = new User();
        user2.setName("Josh");
        user2.setUserId(3L);

        repository.saveFriend(user1.getUserId(), user2.getUserId());

        //어느 순서로 해도 똑같음.
        Assertions.assertThat(repository.checkIsFriend(user1.getUserId(),user2.getUserId())).isEqualTo(true);
        Assertions.assertThat(repository.checkIsFriend(user2.getUserId(),user1.getUserId())).isEqualTo(true);

    }

    @Test
    public void deleteFriend(){
        User user1 = new User();
        user1.setName("Alex");
        user1.setUserId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setUserId(2L);


        repository.saveFriend(user1.getUserId(), user2.getUserId());
        repository.deleteFriend(user1.getUserId(),user2.getUserId());

        Assertions.assertThat(repository.checkIsFriend(user1.getUserId(), user2.getUserId())).isEqualTo(false);

    }
}
