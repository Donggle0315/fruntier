package com.fruntier.fruntier.running.domain;

public class Edge {
    private Long id;
    private Vertex startVertex;
    private Vertex endVertex;
    private Double distance;

    /* additional information about the edge */
    private Integer slope;
    private Integer width;
    private Integer population;
    private Double subjective_score;
    private Double total_score;

    public Edge(Long id, Vertex startVertex, Vertex endVertex, Double distance, Integer slope, Integer width, Integer population, Double subjective_score, Double total_score) {
        this.id = id;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.distance = distance;
        this.slope = slope;
        this.width = width;
        this.population = population;
        this.subjective_score = subjective_score;
        this.total_score = total_score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getSlope() {
        return slope;
    }

    public void setSlope(Integer slope) {
        this.slope = slope;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getSubjective_score() {
        return subjective_score;
    }

    public void setSubjective_score(Double subjective_score) {
        this.subjective_score = subjective_score;
    }

    public Double getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Double total_score) {
        this.total_score = total_score;
    }
}
