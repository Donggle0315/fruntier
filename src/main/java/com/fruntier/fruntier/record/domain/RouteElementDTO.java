package com.fruntier.fruntier.record.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RouteElementDTO {
    Long routeId;
    Long userId;
    String title;

    public RouteElementDTO(Long routeId, Long userId, String title) {
        this.routeId = routeId;
        this.userId = userId;
        this.title = title;
    }
}
