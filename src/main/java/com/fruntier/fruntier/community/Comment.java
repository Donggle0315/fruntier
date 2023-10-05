package com.fruntier.fruntier.community;

public class Comment {
    private Long comment_id;
    private Long user_id;
    private Long article_id;
    private int date;
    private String content;

    public Comment(Long comment_id, Long user_id, Long article_id, int date, String content) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.article_id = article_id;
        this.date = date;
        this.content = content;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
