package com.fruntier.fruntier.user.repository;

import com.fruntier.fruntier.user.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndPassword(String username,String password);
    Optional<User> findByUsername(String username);
}
