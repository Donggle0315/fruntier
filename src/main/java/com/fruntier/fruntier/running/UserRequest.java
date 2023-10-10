package com.fruntier.fruntier.running;

public class UserRequest {
    //시작 좌표
    Double start_coordinate;
    //끝 좌표
    Double end_coordinate;
    //가장 가까이 있는 시작 점
    Vertex start_vertex;
    //가장 가까이 있는 도착 점
    Vertex end_vertex;

    Integer expect_distance;

    public UserRequest(Double start_coordinate, Double end_coordinate, Vertex start_vertex, Vertex end_vertex, Integer expect_distance) {
        this.start_coordinate = start_coordinate;
        this.end_coordinate = end_coordinate;
        this.start_vertex = start_vertex;
        this.end_vertex = end_vertex;
        this.expect_distance = expect_distance;
    }

    public Double getStart_coordinate() {
        return start_coordinate;
    }

    public void setStart_coordinate(Double start_coordinate) {
        this.start_coordinate = start_coordinate;
    }

    public Double getEnd_coordinate() {
        return end_coordinate;
    }

    public void setEnd_coordinate(Double end_coordinate) {
        this.end_coordinate = end_coordinate;
    }

    public Vertex getStart_vertex() {
        return start_vertex;
    }

    public void setStart_vertex(Vertex start_vertex) {
        this.start_vertex = start_vertex;
    }

    public Vertex getEnd_vertex() {
        return end_vertex;
    }

    public void setEnd_vertex(Vertex end_vertex) {
        this.end_vertex = end_vertex;
    }

    public Integer getExpect_distance() {
        return expect_distance;
    }

    public void setExpect_distance(Integer expect_distance) {
        this.expect_distance = expect_distance;
    }
}
