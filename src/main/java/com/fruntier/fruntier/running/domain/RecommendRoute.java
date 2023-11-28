package com.fruntier.fruntier.running.domain;

import com.fruntier.fruntier.running.domain.Edge;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class RecommendRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double distance;
    private Integer expectedTime;
    private Double score;
    @ManyToMany
    private List<Vertex> routeVertices;
//    @JoinTable(
//            name = "recommend_route_edge",
//            joinColumns = @JoinColumn(name = "recommend_route_id"),
//            inverseJoinColumns = @JoinColumn(name = "edge_id")
//    )
    public RecommendRoute() {
    }

    public RecommendRoute(Long id, Double distance, Integer expectedTime, Double score, List<Vertex> routeVertices) {
        this.id = id;
        this.distance = distance;
        this.expectedTime = expectedTime;
        this.score = score;
        this.routeVertices = routeVertices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Integer expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Vertex> getRouteVertices() {
        return routeVertices;
    }

    public void setRouteVertices(List<Vertex> routeVertices) {
        this.routeVertices = routeVertices;
    }

    @Override
    public String toString() {
        return "RecommendRoute{" +
                "id=" + id +
                ", distance=" + distance +
                ", expectedTime=" + expectedTime +
                ", score=" + score +
                ", routeVertices=" + routeVertices +
                '}';
    }
}
