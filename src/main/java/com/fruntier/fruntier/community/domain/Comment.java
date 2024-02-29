package com.fruntier.fruntier.community.domain;


import com.fruntier.fruntier.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User author;
    private LocalDateTime date;
    private String content;

    @ManyToOne
    private Article article;

    public Comment() {
    }

    public boolean isSameAsAuthor(User user) {
        return getAuthor().equals(user);
    }
}