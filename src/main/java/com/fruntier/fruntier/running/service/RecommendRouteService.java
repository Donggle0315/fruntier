package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;

import java.util.List;

public interface RecommendRouteService {
    void createRecommendRoute(RecommendRoute recommendRoute);

    RecommendRoute findRecommendRouteById(Long recommendRouteId);

    Boolean deleteRecommendRouteById(Long recommendRouteId);

    List<Edge> makeRecommendRouteNormal(UserRequest userRequest);
}
