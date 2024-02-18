package com.fruntier.fruntier.user.repository;

import com.fruntier.fruntier.user.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
