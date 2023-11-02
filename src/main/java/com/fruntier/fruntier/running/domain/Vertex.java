package com.fruntier.fruntier.running.domain;

import java.util.List;

public class Vertex {
    private Long id;
    private Coordinate coordinate;
    private String location;
    private List<Edge> outEdge;

    public Vertex(Long id, Coordinate coordinate, String location, List<Edge> outEdge) {
        this.id = id;
        this.coordinate = coordinate;
        this.location = location;
        this.outEdge = outEdge;
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

    public List<Edge> getOutEdge() {
        return outEdge;
    }

    public void setOutEdge(List<Edge> outEdge) {
        this.outEdge = outEdge;
    }
}
