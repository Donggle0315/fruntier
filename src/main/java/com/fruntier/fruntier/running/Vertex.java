package com.fruntier.fruntier.running;

public class Vertex {
    private Long id;
    private Coordinate coordinate;
    private String location;

    public Vertex(Long id, Coordinate coordinate, String location) {
        this.id = id;
        this.coordinate = coordinate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
