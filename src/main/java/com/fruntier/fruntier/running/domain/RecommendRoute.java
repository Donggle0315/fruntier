package com.fruntier.fruntier.running.domain;

import com.fruntier.fruntier.running.domain.Edge;

import java.util.List;

public class RecommendRoute {
    private Long id;
    private Double distance;
    private Integer expected_time;
    private Double score;
    private List<Edge> route_edges;

    public RecommendRoute(Long id, Double distance, int expected_time, Double score, List<Edge> route_edges) {
        this.id = id;
        this.distance = distance;
        this.expected_time = expected_time;
        this.score = score;
        this.route_edges = route_edges;
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

    public Integer getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(Integer expected_time) {
        this.expected_time = expected_time;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Edge> getRoute_edges() {
        return route_edges;
    }

    public void setRoute_edges(List<Edge> route_edges) {
        this.route_edges = route_edges;
    }
}
