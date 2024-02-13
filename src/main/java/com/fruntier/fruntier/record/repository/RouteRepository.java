package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository {
    Route save(Route route);

    Route findById(Long id);

    List<Route> findAll();
}
