package com.fruntier.fruntier.community.domain;

import java.util.Date;

public class ArticleDTO {
    private String title;
    private String content;
    private String status;
    private String type;

    public ArticleDTO(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArticleType getArticleType() {
        if(type.equals("RUN_TOGETHER")){
            return ArticleType.RUN_TOGETHER;
        }
        return ArticleType.NORMAL;
    }
}
