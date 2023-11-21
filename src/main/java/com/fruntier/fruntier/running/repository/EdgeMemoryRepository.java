package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class EdgeMemoryRepository{

    Map<Long,Edge> edgeStore = new HashMap<>();
    public Edge save(Edge edge) throws IllegalArgumentException{
        //check if argument is null
        if(edge == null){
            throw new IllegalArgumentException("Argument(edge) is null");
        }
        //add edge to DB
        edgeStore.put(edge.getId(),edge);
        return edge;
    }

    public Optional<Edge> findById(Long edgeId) throws IllegalArgumentException{
        if(edgeId == null){
            throw new IllegalArgumentException("Argument(edgeId) is null");
        }
        return Optional.ofNullable(edgeStore.get(edgeId));
    }

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
