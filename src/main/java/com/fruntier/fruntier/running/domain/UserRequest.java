package com.fruntier.fruntier.running.domain;

public class UserRequest {
    Vertex startVertex;
    Vertex endVertex;
    Integer expectDistance;

    public UserRequest(Vertex startVertex, Vertex endVertex, Integer expectDistance) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.expectDistance = expectDistance;
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

    public Integer getExpectDistance() {
        return expectDistance;
    }

    public void setExpectDistance(Integer expectDistance) {
        this.expectDistance = expectDistance;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "start_vertex=" + startVertex +
                ", end_vertex=" + endVertex +
                ", expect_distance=" + expectDistance +
                '}';
    }
}