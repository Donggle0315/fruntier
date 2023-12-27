package com.fruntier.fruntier.user;

import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.repository.MemoryFriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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
        user1.setId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setId(2L);

        Assertions.assertThat(repository.checkIsFriend(user1.getId(),user2.getId())).isEqualTo(false);

        repository.saveFriend(user1.getId(),user2.getId());

        Assertions.assertThat(repository.checkIsFriend(user1.getId(),user2.getId())).isEqualTo(true);

    }
    @Test
    public void saveFriend(){
        User user1 = new User();
        user1.setName("Alex");
        user1.setId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setId(2L);

        User user3 = new User();
        user2.setName("Josh");
        user2.setId(3L);

        repository.saveFriend(user1.getId(), user2.getId());

        //어느 순서로 해도 똑같음.
        Assertions.assertThat(repository.checkIsFriend(user1.getId(),user2.getId())).isEqualTo(true);
        Assertions.assertThat(repository.checkIsFriend(user2.getId(),user1.getId())).isEqualTo(true);

    }

    @Test
    public void deleteFriend(){
        User user1 = new User();
        user1.setName("Alex");
        user1.setId(1L);

        User user2 = new User();
        user2.setName("Liam");
        user2.setId(2L);


        repository.saveFriend(user1.getId(), user2.getId());
        repository.deleteFriend(user1.getId(),user2.getId());

        Assertions.assertThat(repository.checkIsFriend(user1.getId(), user2.getId())).isEqualTo(false);

    }
}
