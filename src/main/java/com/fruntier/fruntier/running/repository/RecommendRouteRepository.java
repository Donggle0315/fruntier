package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.RecommendRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRouteRepository extends JpaRepository<RecommendRoute, Long> {

}
