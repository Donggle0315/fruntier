package com.fruntier.fruntier.running.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@Entity
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "start_vertex_id")
    private Vertex startVertex;
    @ManyToOne
    @JoinColumn(name = "end_vertex_id")
    private Vertex endVertex;
    private Double distance;


    public Edge(){}
}
