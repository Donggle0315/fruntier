package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordSaveServiceImpl implements RecordSaveService {
    private final RouteRepository routeRepository;

    @Autowired
    public RecordSaveServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route save(Route route) {
        Route save = routeRepository.save(route);
        return save;
    }
}
