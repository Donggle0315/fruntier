package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VertexDTO {
    private String title;
    private Coordinate coordinate;

    public VertexDTO(String title, Coordinate coordinate) {
        this.title = title;
        this.coordinate = coordinate;
    }
}
