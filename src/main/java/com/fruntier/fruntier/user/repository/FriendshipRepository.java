package com.fruntier.fruntier.user.repository;

import com.fruntier.fruntier.user.domain.Friendship;
import com.fruntier.fruntier.user.domain.FriendKey;
import com.fruntier.fruntier.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendKey> {
    List<User> findAllByUser1(User user);
}
