package com.fruntier.fruntier.running.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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


    @Override
    public boolean equals(Object vertex){
        if (vertex.getClass() == Vertex.class){
            return this.getId().equals(((Vertex)vertex).getId());
        }
        return false;
    }
}
