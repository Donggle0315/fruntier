package com.fruntier.fruntier.running.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coordinate coordinate;
    private String location;

    @OneToMany(mappedBy = "endVertex")
    private List<Edge> inEdge;
    @OneToMany(mappedBy = "startVertex")
    private List<Edge> outEdge;

    public Vertex(){}

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

    public List<Edge> getInEdge() {
        return inEdge;
    }

    public void setInEdge(List<Edge> inEdge) {
        this.inEdge = inEdge;
    }

    public List<Edge> getOutEdge() {
        return outEdge;
    }

    public void setOutEdge(List<Edge> outEdge) {
        this.outEdge = outEdge;
    }
}
