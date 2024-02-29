package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Record;
import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.repository.RecordRepository;
import com.fruntier.fruntier.record.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecordUpdateServiceImpl implements RecordUpdateService {
    private final RouteRepository routeRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public RecordUpdateServiceImpl(RouteRepository routeRepository, RecordRepository recordRepository) {
        this.routeRepository = routeRepository;
        this.recordRepository = recordRepository;
    }

    @Override
    public Record updateRecord(Long routeId, Record record) throws NoSuchElementException {
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new NoSuchElementException()
        );

        route.getHistory().add(record);
        recordRepository.save(record);
        return record;
    }
}
