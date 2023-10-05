package com.fruntier.fruntier.running;

public class Vertex {
    private Long id;
    private Double x_coordinate;
    private Double y_coordinate;
    private String location;

    public Vertex(Long id, Double x_coordinate, Double y_coordinate, String location) {
        this.id = id;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(Double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public Double getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(Double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
