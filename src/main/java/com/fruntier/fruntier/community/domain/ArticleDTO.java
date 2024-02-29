package com.fruntier.fruntier.community.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
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

    public ArticleType getArticleType() {
        if(type.equals("RUN_TOGETHER")){
            return ArticleType.RUN_TOGETHER;
        }
        return ArticleType.NORMAL;
    }
}
