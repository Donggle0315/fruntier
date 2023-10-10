package com.fruntier.fruntier.running;

import java.util.List;

public interface RecommendRouteService {
    void createRecommendRoute(RecommendRoute recommendRoute);

    RecommendRoute findRecommendRouteById(Long recommendRouteId);

    Boolean deleteRecommendRouteById(Long recommendRouteId);

    List<Edge> makeRecommendRouteNormal(UserRequest userRequest);
}
