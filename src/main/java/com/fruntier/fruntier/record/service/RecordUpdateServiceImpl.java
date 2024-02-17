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
    private final RouteRetrieveService routeRetrieveService;

    @Autowired
    public RecordUpdateServiceImpl(RouteRepository routeRepository, RecordRepository recordRepository, RouteRetrieveService routeRetrieveService) {
        this.routeRepository = routeRepository;
        this.recordRepository = recordRepository;
        this.routeRetrieveService = routeRetrieveService;
    }

    @Override
    public Record updateRecord(Long routeId, Record record) {
        Route route = routeRepository.findById(routeId).get();

        if (route == null) {
            throw new NoSuchElementException();
        }

        if (route.getHistory() == null) {
            route.setHistory(new ArrayList<>());
        }

        route.getHistory().add(record);
        recordRepository.save(record);
        return record;
    }

    @Override
    public Record getRecordById(Long Id) {
        Record record = recordRepository.findById(Id).get();

        return record;
    }

    @Override
    public int getNumberOfPeopleRunning(Long routeId) {
        List<Record> records = recordRepository.findByRouteId(routeId);
        if (records == null) {
            return 0;
        }
        return records.size();
    }

    @Override
    public int getAverageMinutesRunning(Long routeId) {
        List<Record> records = recordRepository.findByRouteId(routeId);
        if (records == null) {
            return 0;
        }
        int sum = 0;
        for (Record record : records) {
            sum += record.getMinutesForRunning();
        }
        double avg = (double) sum / records.size();
        return (int)avg;
    }
}
