package com.fruntier.fruntier.community;

public class ArticleRecommendRoute extends Article{
    private Long route_id;
    private String comment;

    public ArticleRecommendRoute(Long id, boolean isVisible, String mode, String content, int date, Long user_id, int recommend_number, Long route_id, String comment) {
        super(id, isVisible, mode, content, date, user_id, recommend_number);
        this.route_id = route_id;
        this.comment = comment;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
