package com.fruntier.fruntier.running;

import java.util.ArrayList;

import static java.util.Arrays.stream;

public class VertexMemoryRepository implements VertexRepository {
    ArrayList<Vertex> vertex_list = new ArrayList<>();

    @Override
    public void saveVertex(Vertex vertex) {
        vertex_list.add(vertex);
    }

    @Override
    public Vertex findVertexById(Long vertexId) {
        for(Vertex v : vertex_list){
            if(v.getId() == vertexId){
                return v;
            }
        }
        return null;
    }


    @Override
    public Boolean deleteVertex(Long vertexId) {
        return null;
    }
}
