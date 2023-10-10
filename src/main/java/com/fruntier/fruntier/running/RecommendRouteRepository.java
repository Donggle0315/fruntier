package com.fruntier.fruntier.running;

public interface RecommendRouteRepository {
    void saveRecoRoute(RecommendRoute recommendRoute);

    RecommendRoute getRecoRouteById(Long recoId);

    boolean deleteRecoRoute(Long recoId);
}
