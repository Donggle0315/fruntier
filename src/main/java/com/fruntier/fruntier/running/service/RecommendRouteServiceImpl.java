package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.*;
import com.fruntier.fruntier.running.exception.NotFindRecommendRouteException;
import com.fruntier.fruntier.running.repository.RecommendRouteRepository;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendRouteServiceImpl implements RecommendRouteService {
    private final RecommendRouteRepository recommendRouteRepository;
    private final GraphService graphService;
    public final VertexRepository vertexRepository;
    private final double DISTANCE_LOWER_PERCENTAGE = 0.95;

    @Autowired
    public RecommendRouteServiceImpl(RecommendRouteRepository recommendRouteRepository, GraphService graphService, VertexRepository vertexRepository) {
        this.recommendRouteRepository = recommendRouteRepository;
        this.graphService = graphService;
        this.vertexRepository = vertexRepository;
    }

    @Override
    public void createRecommendRoute(RecommendRoute recommendRoute) {
        recommendRouteRepository.save(recommendRoute);
    }

    @Override
    public RecommendRoute findRecommendRouteById(Long recommendRouteId) {
        Optional<RecommendRoute> recommendRoute = recommendRouteRepository.findById(recommendRouteId);
        return recommendRoute.orElse(null);
    }

    @Override
    public Boolean deleteRecommendRouteById(Long recommendRouteId) {
        if(recommendRouteRepository.findById(recommendRouteId).isPresent()){
            recommendRouteRepository.deleteById(recommendRouteId);
            return true;
        }
        return false;
    }

    @Override
    public List<Vertex> makeRecommendRouteNormal(UserRequest userRequest) throws NotFindRecommendRouteException{
        return findNormalRoute(userRequest);
    }

    @Override
    public RecommendRoute saveRoute(RecommendRoute recommendRoute) {
        RecommendRoute route = recommendRouteRepository.save(recommendRoute);
        return route;
    }


    class StackElement {
        public Vertex vertex;
        public Double distance;
        public Queue<Edge> edges;

        public StackElement(Vertex vertex, Double distance, Collection<Edge> collection) {
            this.vertex = vertex;
            this.distance = distance;
            this.edges = new PriorityQueue<>((Edge e1, Edge e2) -> e1.getDistance() > e2.getDistance()?1:-1);
            this.edges.addAll(collection);
        }
    }

    public List<Vertex> findNormalRoute(UserRequest userRequest) throws NotFindRecommendRouteException {
        Vertex startVertex = userRequest.getStartVertex();
        Vertex endVertex = userRequest.getEndVertex();
        Stack<StackElement> route = new Stack<>();

        List<Edge> startConnectedEdge = graphService.findConnectedEdge(startVertex);
        route.add(new StackElement(startVertex, 0D, startConnectedEdge));

        while(!route.empty()){
            StackElement cur = route.peek();

            if(cur.vertex.equals(endVertex) && isInRange(cur.distance, userRequest.getExpectDistance())) {
                break;
            }
            if(shortestPathDistance(cur.vertex, endVertex) >
                    userRequest.getExpectDistance() - cur.distance){
                route.pop();
                continue;
            }

            Edge edge = cur.edges.poll();
            // no edges left -> pop
            if(edge == null){
                route.pop();
                continue;
            }

            StackElement nextVertex = new StackElement(
                    edge.getEndVertex(),
                    cur.distance + edge.getDistance(),
                    graphService.findConnectedEdge(edge.getEndVertex())
            );
            route.push(nextVertex);
        }

        // fail
        if(route.empty()){
            throw new NotFindRecommendRouteException("Fail To Make Recommend Route");
        }
        // success
        return convertRouteToVertex(route);
    }

    public boolean isInRange(double distance, double expectedDistance){
        return Math.abs(distance-expectedDistance) < expectedDistance*0.2;
    }

    public double shortestPathDistanceHeuristic(Vertex startVertex, Vertex endVertex){
        Coordinate coordinate1 = startVertex.getCoordinate();
        Coordinate coordinate2 = endVertex.getCoordinate();
        double xDiff = coordinate1.getLatitude() - coordinate2.getLatitude();
        double yDiff = coordinate1.getLongitude() - coordinate2.getLongitude();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff) * 1.5;
    }

    public double shortestPathDistance(Vertex startVertex, Vertex endVertex){
        Map<Vertex, Double> distances = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();
        Set<Vertex> visitedVertices = new HashSet<>();

        // Initialize distances with infinity and startVertex with distance 0
        for (Vertex vertex : vertexRepository.findAll()) {
            distances.put(vertex, Double.MAX_VALUE);
        }
        distances.put(startVertex, 0.0);

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            visitedVertices.add(currentVertex);

            for (Edge edge : graphService.findConnectedEdge(currentVertex)) {
                Vertex neighbor = edge.getEndVertex();
                if (!visitedVertices.contains(neighbor)) {
                    double newDistance = distances.get(currentVertex) + edge.getDistance();
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        predecessors.put(neighbor, currentVertex);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }
        return distances.get(endVertex);
    }

    private List<Vertex> convertRouteToVertex(Stack<StackElement> route){
        List<Vertex> convertedList = new ArrayList<>();
        for (StackElement elem : route){
            convertedList.add(elem.vertex);
        }

        return convertedList;
    }



    private Vertex getLastVertex(List<Edge> edges, Vertex userStartVertex) {
        if (edges.isEmpty()) {
            return userStartVertex;
        }
        return edges.get(edges.size() - 1).getEndVertex();
    }
    private boolean checkFinishCondition(UserRequest userRequest, List<Edge> edges, Double distance) {

        return distance >= userRequest.getExpectDistance() * DISTANCE_LOWER_PERCENTAGE &&
                edges.get(edges.size() - 1).getEndVertex().equals(userRequest.getEndVertex());
    }
}
