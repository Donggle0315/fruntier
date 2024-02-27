package com.fruntier.fruntier.record.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDateTime runningDate;
    private int minutesForRunning;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Record() {
    }

    public Record(Long id, Long userId, LocalDateTime runningDate, int minutesForRunning, Route route) {
        this.id = id;
        this.userId = userId;
        this.runningDate = runningDate;
        this.minutesForRunning = minutesForRunning;
        this.route = route;
    }
}
