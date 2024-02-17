package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;

import java.util.List;

public interface RouteRetrieveService {

    List<Route> listRoutesAllNormal();

    Route getRouteFoundById(Long id);
}
