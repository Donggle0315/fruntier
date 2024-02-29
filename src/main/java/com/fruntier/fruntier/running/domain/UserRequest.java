package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserRequest {
    Vertex startVertex;
    Vertex endVertex;
    Integer expectDistance;

    public UserRequest() {
    }

    public UserRequest(Vertex startVertex, Vertex endVertex, Integer expectDistance) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.expectDistance = expectDistance;
    }
}