package com.fruntier.fruntier.running.service;


import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.VertexMemoryRepository;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GraphServiceImpl implements GraphService {
    private final VertexRepository vertexRepository;

    @Autowired
    public GraphServiceImpl(VertexRepository vertexRepository) {
        this.vertexRepository = vertexRepository;
    }



    @Override
    public List<Edge> findConnectedEdge(Vertex vertex) {
        Vertex resultVertex;
        List<Edge> returnEdgeList;
        Optional<Vertex> v1 = vertexRepository.findById(vertex.getId());
        if(v1.isPresent()){
            resultVertex = v1.get();
        }else{
            return null;
        }

        returnEdgeList = resultVertex.getOutEdge();

        return returnEdgeList;

    }
}
