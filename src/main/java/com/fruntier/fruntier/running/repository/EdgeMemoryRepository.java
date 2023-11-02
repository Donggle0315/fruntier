package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EdgeMemoryRepository implements EdgeRepository {

    Map<Long,Edge> edgeStore = new HashMap<>();
    @Override
    public Edge save(Edge edge) throws IllegalArgumentException{
        //check if argument is null
        if(edge == null){
            throw new IllegalArgumentException("Argument(edge) is null");
        }
        //value alredy exists
        if(edgeStore.containsKey(edge.getId())){

        }
        //add edge to DB
        edgeStore.put(edge.getId(),edge);
        return edge;
    }

    @Override
    public Optional<Edge> findById(Long edgeId) throws IllegalArgumentException{
        if(edgeId == null){
            throw new IllegalArgumentException("Argument(edgeId) is null");
        }
        return Optional.ofNullable(edgeStore.get(edgeId));
    }

    @Override
    public void delete(Long edgeId) throws IllegalArgumentException{
        if(edgeId == null){
            throw new IllegalArgumentException("Argument(edgeId) is null");
        }
        edgeStore.remove(edgeId);
    }

    public void clear(){
        edgeStore.clear();
    }
}
