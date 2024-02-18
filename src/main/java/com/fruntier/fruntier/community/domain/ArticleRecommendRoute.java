package com.fruntier.fruntier.community.domain;

import com.fruntier.fruntier.community.domain.Article;

public class ArticleRecommendRoute extends Article {
    private Long route_id;
    public ArticleRecommendRoute(){}

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }
}
