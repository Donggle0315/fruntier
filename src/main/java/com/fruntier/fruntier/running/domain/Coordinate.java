package com.fruntier.fruntier.running.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Embeddable
public class Coordinate {
    private Double longitude;
    private Double latitude;

    public Coordinate() {
    }

    public Coordinate(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
