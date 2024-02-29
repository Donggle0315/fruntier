package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RouteRetrieveServiceImpl implements RouteRetrieveService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteRetrieveServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> listRoutesAllNormal() {
        return routeRepository.findAll();
    }

    @Override
    public Route findRouteById(Long id) throws NoSuchElementException{
        return routeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException()
        );
    }
}
