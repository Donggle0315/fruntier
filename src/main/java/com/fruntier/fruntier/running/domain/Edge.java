package com.fruntier.fruntier.running.domain;


import jakarta.persistence.*;

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

    public Double getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(Double subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString(){
        return  this.id + " " +
                this.startVertex.getId() + " " +
                this.endVertex.getId() + " " +
                this.distance + " " +
                this.slope + " " +
                this.width + " " +
                this.population + " " +
                this.subjectiveScore + " " +
                this.totalScore + " ";
    }
}
