package com.fruntier.fruntier.record.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime runningDate;
    private int minutesForRunning;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Record() {
    }

    public Record(Long id, LocalDateTime runningDate, int minutesForRunning, Route route) {
        this.id = id;
        this.runningDate = runningDate;
        this.minutesForRunning = minutesForRunning;
        this.route = route;
    }

    @Override
    public String toString() {
        return this.id + " " + this.minutesForRunning + " " + this.runningDate + " " + this.route;
    }
}
