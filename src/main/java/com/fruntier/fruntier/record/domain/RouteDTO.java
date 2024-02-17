package com.fruntier.fruntier.record.domain;

import com.fruntier.fruntier.running.domain.RecommendRoute;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RouteDTO {
    private Long userId;
    private String title;
    private RecommendRoute recommendRoute;

    public RouteDTO() {
    }

    public RouteDTO(Long userId, String title, RecommendRoute recommendRoute) {
        this.userId = userId;
        this.title = title;
        this.recommendRoute = recommendRoute;
    }
}
