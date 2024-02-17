package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class RecommendRouteDTO {
    private Long id;
    private List<UserPoint> userPointList;

    public RecommendRouteDTO() {
    }

    public RecommendRouteDTO(Long id, List<UserPoint> userPointList) {
        this.id = id;
        this.userPointList = userPointList;
    }
}
