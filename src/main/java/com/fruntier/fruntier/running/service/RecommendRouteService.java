package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.exception.NotFindRecommendRouteException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecommendRouteService {
    void createRecommendRoute(RecommendRoute recommendRoute);

    RecommendRoute findRecommendRouteById(Long recommendRouteId) throws NotFindRecommendRouteException;

    Boolean deleteRecommendRouteById(Long recommendRouteId);

    List<Vertex> makeRecommendRouteNormal(UserRequest userRequest) throws NotFindRecommendRouteException;

    void saveRoute(RecommendRoute recommendRoute) throws NotFindRecommendRouteException;
}
