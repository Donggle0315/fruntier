package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Record;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryRecordRepositoryTest {
    private RecordRepository repository = new MemoryRecordRepository();

    @Test
    void saveNormaRecord() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Record record = new Record(1L, localDateTime, 300, null);
        Record result = repository.save(record);

        assertThat(result.getId()).isEqualTo(record.getId());
        assertThat(result.getRunningDate()).isEqualTo(record.getRunningDate());
        assertThat(result.getMinutesForRunning()).isEqualTo(record.getMinutesForRunning());
        assertThat(result.getRoute()).isEqualTo(record.getRoute());
    }

    @Test
    void saveNullRecord() {
        assertThrows(NoSuchElementException.class, () -> repository.save(null));
    }

    @Test
    void saveNonElementRecord() {
    }

}