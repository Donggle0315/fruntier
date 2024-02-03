package com.fruntier.fruntier.community.domain;

import java.util.Date;

public class ArticleDTO {
    private String title;
    private String content;
    private String status;

    public ArticleDTO(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
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
}
