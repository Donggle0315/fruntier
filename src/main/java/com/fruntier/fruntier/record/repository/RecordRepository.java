package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Record;

import java.util.List;

public interface RecordRepository1 {
    Record save(Record record);

    Record findById(Long id);

    List<Record> findByRouteId(Long routeId);
}
