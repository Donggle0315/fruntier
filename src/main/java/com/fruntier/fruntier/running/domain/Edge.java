package com.fruntier.fruntier.running.domain;

public class Edge {
    private Long id;
    private Long start_vertex_id;
    private Long end_vertex_id;
    private Double distance;

    /* additional information about the edge */
    private Integer slope;
    private Integer width;
    private Integer population;
    private Double subjective_score;
    private Double total_score;

    public Edge(Long id, Long start_vertex_id, Long end_vertex_id, Double distance, int slope, int width, int population, Double subjective_score, Double total_score) {
        this.id = id;
        this.start_vertex_id = start_vertex_id;
        this.end_vertex_id = end_vertex_id;
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

    public Long getStart_vertex_id() {
        return start_vertex_id;
    }

    public void setStart_vertex_id(Long start_vertex_id) {
        this.start_vertex_id = start_vertex_id;
    }

    public Long getEnd_vertex_id() {
        return end_vertex_id;
    }

    public void setEnd_vertex_id(Long end_vertex_id) {
        this.end_vertex_id = end_vertex_id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
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

    @Override
    public String toString(){
        return  this.id + " " +
                this.start_vertex_id + " " +
                this.end_vertex_id + " " +
                this.distance + " " +
                this.slope + " " +
                this.width + " " +
                this.population + " " +
                this.subjective_score + " " +
                this.total_score;
    }
}
