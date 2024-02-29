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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double distance;
    private Integer expectedTime;
    private Double score;
    private Long userId;
    @ManyToMany
    private List<Vertex> routeVertices = new ArrayList<>();

    public RecommendRoute() {
    }

    public RecommendRoute(Long id, Double distance, Integer expectedTime, Double score, List<Vertex> routeVertices, Long userId) {
        this.id = id;
        this.distance = distance;
        this.expectedTime = expectedTime;
        this.score = score;
        this.routeVertices = routeVertices;
        this.userId = userId;
    }
}
