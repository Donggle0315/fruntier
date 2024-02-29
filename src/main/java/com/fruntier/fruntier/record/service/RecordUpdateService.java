package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Record;

import java.util.NoSuchElementException;

public interface RecordUpdateService {
    Record updateRecord(Long routeId, Record record) throws NoSuchElementException;
}
