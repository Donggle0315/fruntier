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
    /* additional information about the edge */
    private Integer slope;
    private Integer width;
    private Integer population;
    private Double subjectiveScore;
    @Transient
    private Double totalScore;

    public Edge(){}

    public Edge(Long id, Vertex startVertex, Vertex endVertex, Double distance, Integer slope, Integer width, Integer population, Double subjectiveScore, Double totalScore) {
        this.id = id;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.distance = distance;
        this.slope = slope;
        this.width = width;
        this.population = population;
        this.subjectiveScore = subjectiveScore;
        this.totalScore = totalScore;
    }
}
