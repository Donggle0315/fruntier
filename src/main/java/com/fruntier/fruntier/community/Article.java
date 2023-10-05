package com.fruntier.fruntier.community;

public class Article {
    private Long id;
    private boolean isVisible;
    private String mode;
    private String content;
    private int date;
    private Long user_id;
    private int recommend_number;

    public Article(Long id, boolean isVisible, String mode, String content, int date, Long user_id, int recommend_number) {
        this.id = id;
        this.isVisible = isVisible;
        this.mode = mode;
        this.content = content;
        this.date = date;
        this.user_id = user_id;
        this.recommend_number = recommend_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getRecommend_number() {
        return recommend_number;
    }

    public void setRecommend_number(int recommend_number) {
        this.recommend_number = recommend_number;
    }
}
