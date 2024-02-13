package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository {
    Record save(Record record);

    Record findById(Long id);

    List<Record> findByRouteId(Long routeId);
}
