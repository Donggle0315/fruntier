package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public UserRequest makeUserRequesetFromJSON(Map<String, Object> payload) {
        // Process the received JSON data on the server
        int expectedDistance = (int) payload.get("expectedDistance");
        List<Map<String, Object>> vertices = (List<Map<String, Object>>) payload.get("vertices");

        // Extract start and end coordinates
        Coordinate startCoordinate = convertToCoordinate(vertices.get(0).get("coordinate"));
        Coordinate endCoordinate = convertToCoordinate(vertices.get(1).get("coordinate"));
        Vertex startVertex = convertCoordinateToVertex(startCoordinate);
        Vertex endVertex = convertCoordinateToVertex(endCoordinate);

        UserRequest userRequest = new UserRequest(startVertex, endVertex, expectedDistance);
        System.out.println("userRequest : " + userRequest);

        return userRequest;
    }

    private Coordinate convertToCoordinate(Object coordinateData) {
        Map<String, Double> coordinateMap = (Map<String, Double>) coordinateData;
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(coordinateMap.get("latitude"));
        coordinate.setLongitude(coordinateMap.get("longitude"));
        return coordinate;
    }

    public Vertex findNearestVertex(Coordinate targetCoordinate) {
        List<Vertex> allVertices = vertexRepository.findAll(); // Fetch all vertices from the database

        Vertex nearestVertex = null;
        double minDistance = Double.MAX_VALUE;

        for (Vertex vertex : allVertices) {
            double distance = calculateDistance(targetCoordinate, vertex.getCoordinate());
            if (distance < minDistance) {
                minDistance = distance;
                nearestVertex = vertex;
            }
        }

        return nearestVertex;
    }

    // Add a method to calculate distance between two coordinates
    private double calculateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        // Implement your distance calculation logic (e.g., Euclidean distance, Haversine formula, etc.)
        // Here's a simple example using Euclidean distance:
        double xDiff = coordinate1.getLatitude() - coordinate2.getLatitude();
        double yDiff = coordinate1.getLongitude() - coordinate2.getLongitude();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
