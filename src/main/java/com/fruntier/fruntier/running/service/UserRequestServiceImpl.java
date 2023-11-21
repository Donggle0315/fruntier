package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestServiceImpl implements UserRequestService {
    private final VertexRepository vertexRepository;

    @Autowired
    public UserRequestServiceImpl(VertexRepository vertexRepository){
        this.vertexRepository = vertexRepository;
    }

    @Override
    public Vertex convertCoordinateToVertex(Coordinate coordinate) {
        Vertex curClosest = null;
        double minDistance = Double.MAX_VALUE;
        for(Vertex vertex: vertexRepository.findAll()){
            Coordinate vertexCoordinate = vertex.getCoordinate();
            double curDistance =
                    Math.pow(vertexCoordinate.getLatitude()-coordinate.getLatitude(),2) +
                    Math.pow(vertexCoordinate.getLongitude()-coordinate.getLongitude(), 2);
            if(curDistance < minDistance){
                minDistance = curDistance;
                curClosest = vertex;
            }
        }

        return curClosest;
    }
}
