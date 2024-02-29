package com.fruntier.fruntier.record.repository;

import com.fruntier.fruntier.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long>{
    List<Record> findByRouteId(Long routeId);
}
