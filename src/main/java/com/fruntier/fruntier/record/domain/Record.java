package com.fruntier.fruntier.record.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRunningDate() {
        return runningDate;
    }

    public void setRunningDate(LocalDateTime runningDate) {
        this.runningDate = runningDate;
    }

    public int getMinutesForRunning() {
        return minutesForRunning;
    }

    public void setMinutesForRunning(int minutesForRunning) {
        this.minutesForRunning = minutesForRunning;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return this.id + " " + this.minutesForRunning + " " + this.runningDate + " " + this.route;
    }
}
