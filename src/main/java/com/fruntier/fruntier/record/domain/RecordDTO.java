package com.fruntier.fruntier.record.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class RecordDTO {
    private String runningDate;
    private int minutesForRunning;
    private Long routeId;

    public RecordDTO() {
    }

    public RecordDTO(String runningDate, int minutesForRunning, Long routeId) {
        this.runningDate = runningDate;
        this.minutesForRunning = minutesForRunning;
        this.routeId = routeId;
    }
}
