package com.fruntier.fruntier.record.service;

import com.fruntier.fruntier.record.domain.Record;

public interface RecordUpdateService {
    Record updateRecord(Long routeId, Record record);

    Record getRecordById(Long Id);

    int getNumberOfPeopleRunning(Long routeId);

    int getAverageMinutesRunning(Long routeId);
}
