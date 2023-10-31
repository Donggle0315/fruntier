package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.RecommendRoute;

public interface RecommendRouteRepository {
    void saveRecoRoute(RecommendRoute recommendRoute);

    RecommendRoute getRecoRouteById(Long recoId);

    boolean deleteRecoRoute(Long recoId);
}
