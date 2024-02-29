package com.fruntier.fruntier.running.service;


import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GraphServiceImpl implements GraphService {
    private final VertexRepository vertexRepository;

    @Autowired
    public GraphServiceImpl(VertexRepository vertexRepository) {
        this.vertexRepository = vertexRepository;
    }

    @Override
    public List<Edge> findConnectedEdge(Vertex vertex) throws NoSuchElementException {
        Vertex resultVertex = vertexRepository.findById(vertex.getId()).orElseThrow(
                () -> new NoSuchElementException("Cannot find Vertex")
        );

        return resultVertex.getOutEdge();
    }
}
