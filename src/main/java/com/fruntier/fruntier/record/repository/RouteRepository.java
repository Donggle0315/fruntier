package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

//    Route findById(Long id);
//    Route save(Route route);
//
//    List<Route> findAll();
}
