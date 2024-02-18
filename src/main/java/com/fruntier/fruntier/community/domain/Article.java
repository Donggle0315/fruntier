package com.fruntier.fruntier.community.domain;


import com.fruntier.fruntier.user.domain.User;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;
    private ArticleStatus status;
    private String title;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}