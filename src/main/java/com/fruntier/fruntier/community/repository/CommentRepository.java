package com.fruntier.fruntier.community.repository;

import com.fruntier.fruntier.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
