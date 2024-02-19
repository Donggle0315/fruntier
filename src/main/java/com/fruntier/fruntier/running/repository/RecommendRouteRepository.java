package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.RecommendRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendRouteRepository extends JpaRepository<RecommendRoute, Long> {

}
