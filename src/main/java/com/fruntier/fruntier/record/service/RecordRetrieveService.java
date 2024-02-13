package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Route;

import java.util.List;

public interface RecordRetrieveService {
    List<Route> listRoutesAllNormal(int pageIndex);

    Route getRouteById(Long id);
}
