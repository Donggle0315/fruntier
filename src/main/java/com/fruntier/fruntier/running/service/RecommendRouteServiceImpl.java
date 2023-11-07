package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;
import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.RecommendRouteFileRepository;
import com.fruntier.fruntier.running.repository.RecommendRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecommendRouteServiceImpl implements RecommendRouteService {
    private final RecommendRouteRepository recommendRouteRepository;
    private final GraphService graphService;
    private final double DISTANCE_LOWER_PERCENTAGE = 0.95;

    @Autowired
    public RecommendRouteServiceImpl(RecommendRouteRepository recommendRouteRepository, GraphService graphService) {
        this.recommendRouteRepository = recommendRouteRepository;
        this.graphService = graphService;
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
    public List<Edge> makeRecommendRouteNormal(UserRequest userRequest) {
        return dfsRouteFinder(userRequest, new ArrayList<>(), 0.0);
    }

    private List<Edge> dfsRouteFinder(UserRequest userRequest, List<Edge> edges, Double distance) {
        if (checkFinishCondition(userRequest,edges,distance)) {
            return edges;
        }

        Vertex lastVertex = getLastVertex(edges, userRequest.getStart_vertex());
        List<Edge> connectedEdges = graphService.findConnectedEdge(lastVertex);

        for (Edge edge : connectedEdges) {
            List<Edge> newEdges = new ArrayList<>();
            newEdges.addAll(edges);
            newEdges.add(edge);
            return dfsRouteFinder(userRequest, newEdges, distance + edge.getDistance());
        }
        return null;
    }

    private Vertex getLastVertex(List<Edge> edges, Vertex userStartVertex) {
        if (edges.isEmpty()) {
            return userStartVertex;
        }
        return edges.get(edges.size() - 1).getEndVertex();
    }
    private boolean checkFinishCondition(UserRequest userRequest, List<Edge> edges, Double distance) {

        return distance >= userRequest.getExpect_distance() * DISTANCE_LOWER_PERCENTAGE &&
                edges.get(edges.size() - 1).getEndVertex().equals(userRequest.getEnd_vertex());
    }
}
