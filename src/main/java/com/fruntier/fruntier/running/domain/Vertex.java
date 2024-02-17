package com.fruntier.fruntier.running.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Coordinate coordinate;
    private String location;

    @ManyToMany(mappedBy = "routeVertices")
    private List<RecommendRoute> route = new ArrayList<>();

    @OneToMany(mappedBy = "endVertex")
    private List<Edge> inEdge;
    @OneToMany(mappedBy = "startVertex")
    private List<Edge> outEdge;

    public Vertex(){}


    @Override
    public boolean equals(Object vertex){
        if (vertex.getClass() == Vertex.class){
            return this.getId().equals(((Vertex)vertex).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", location='" + location + '\'' +
                ", inEdge=" + inEdge +
                ", outEdge=" + outEdge +
                '}';
    }
}
