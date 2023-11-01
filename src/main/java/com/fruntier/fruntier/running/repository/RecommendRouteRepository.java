package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.RecommendRoute;

import java.util.Optional;

public interface RecommendRouteRepository {
    void save(RecommendRoute recommendRoute);

    Optional<RecommendRoute> findById(Long id);

    void delete(RecommendRoute recommendRoute);

    void deleteById(Long id);
}
