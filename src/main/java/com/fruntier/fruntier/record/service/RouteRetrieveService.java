package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;

import java.util.List;
import java.util.NoSuchElementException;

public interface RouteRetrieveService {

    List<Route> listRoutesAllNormal();

    Route findRouteById(Long id) throws NoSuchElementException;
}
