package com.fruntier.fruntier.running.domain;

import com.fruntier.fruntier.running.domain.Vertex;

public class UserRequest {
    Vertex start_vertex;
    Vertex end_vertex;
    Integer expect_distance;

    public UserRequest(Vertex start_vertex, Vertex end_vertex, Integer expect_distance) {
        this.start_vertex = start_vertex;
        this.end_vertex = end_vertex;
        this.expect_distance = expect_distance;
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

    @Override
    public String toString() {
        return "UserRequest{" +
                "start_vertex=" + start_vertex +
                ", end_vertex=" + end_vertex +
                ", expect_distance=" + expect_distance +
                '}';
    }
}
