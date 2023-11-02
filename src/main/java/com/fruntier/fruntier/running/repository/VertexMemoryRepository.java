package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Vertex;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Arrays.stream;

public class VertexMemoryRepository implements VertexRepository {
    ArrayList<Vertex> vertexArrayList = new ArrayList<>();


    @Override
    public Vertex save(Vertex vertex) throws IllegalArgumentException {
        if(vertex == null){
            throw new IllegalArgumentException("Vertex is null");
        }

        vertexArrayList.add(vertex);
        return vertex;
    }

    @Override
    public Optional<Vertex> findById(Long vertexId) throws IllegalArgumentException{

        if(vertexId == null){
            throw new IllegalArgumentException("id is null");
        }

        //search for vertex
        for(Vertex v : vertexArrayList){
            if(v.getId().equals(vertexId)){
                return Optional.of(v);
            }
        }
        //no vertex in DB
        return Optional.empty();
    }

    @Override
    public void delete(Long vertexId) throws IllegalArgumentException{
        if(vertexId == null){
            throw new IllegalArgumentException("id is null");
        }

        vertexArrayList.removeIf(v -> v.getId().equals(vertexId));

    }
}
