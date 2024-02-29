package com.fruntier.fruntier.community.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fruntier.fruntier.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Article")
@Getter
@Setter
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;
    private ArticleStatus status;
    private ArticleType type;
    private String title;

    @JsonIgnore
    @ManyToOne
    private User author;
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public Article() {
    }

    public static Article fromTitle(String searchKey) {
        Article article = new Article();
        article.setTitle(searchKey);
        return article;
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setArticle(this);
    }

    public String getAdaptiveTime() {
        Duration between = Duration.between(date, LocalDateTime.now());
        if(between.toDays() >= 1){
            return date.toLocalDate().toString();
        }
        else{
            LocalTime cur = date.toLocalTime();
            return String.format("%02d:%02d:%02d",
                    cur.getHour(),
                    cur.getMinute(),
                    cur.getSecond());
        }
    }

    public boolean isSameAsAuthor(User user){
        return getAuthor().equals(user);
    }
}