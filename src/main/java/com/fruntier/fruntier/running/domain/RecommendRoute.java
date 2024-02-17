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
@Table(name = "recommend_route")
public class RecommendRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double distance;
    private Integer expectedTime;
    private Double score;
    @ManyToMany(mappedBy = "route", cascade = CascadeType.ALL)
    @JoinTable(
            name = "RecommendRoute_Vertex",
            joinColumns = { @JoinColumn(name = "recommendRoute_id")},
            inverseJoinColumns = { @JoinColumn(name = "vertex_id")}
    )
    private List<Vertex> routeVertices = new ArrayList<>();


    public RecommendRoute() {
    }

    public RecommendRoute(Long id, Double distance, Integer expectedTime, Double score, List<Vertex> routeVertices) {
        this.id = id;
        this.distance = distance;
        this.expectedTime = expectedTime;
        this.score = score;
        this.routeVertices = routeVertices;
    }

}
