package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class MemoryRecordRepository implements RecordRepository {
    private Map<Long, Record> recordMap;
    private Long sequence;

    @Autowired
    public MemoryRecordRepository() {
        recordMap = new HashMap<>();
        sequence = 0L;
    }

    @Override
    public Record save(Record record) {
        if (record == null) {
            throw new NoSuchElementException();
        }
        record.setId(++sequence);
        recordMap.put(sequence, record);

        return record;
    }

    @Override
    public Record findById(Long id) {
        return recordMap.get(id);
    }

    @Override
    public List<Record> findByRouteId(Long routeId) {
        return recordMap.values().stream()
                .toList();
    }
}
