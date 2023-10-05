package com.fruntier.fruntier.running;

import java.util.List;

public class RecommendRoute {
    private Long id;
    private Double distance;
    private int expected_time;
    private Double score;
    private List<Edge> route_info;

    public RecommendRoute(Long id, Double distance, int expected_time, Double score, List<Edge> route_info) {
        this.id = id;
        this.distance = distance;
        this.expected_time = expected_time;
        this.score = score;
        this.route_info = route_info;
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

    public int getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(int expected_time) {
        this.expected_time = expected_time;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Edge> getRoute_info() {
        return route_info;
    }

    public void setRoute_info(List<Edge> route_info) {
        this.route_info = route_info;
    }
}
