package com.fruntier.fruntier;

import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;
import com.fruntier.fruntier.running.repository.RecommendRouteFileRepository;
import com.fruntier.fruntier.running.repository.RecommendRouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestMain {
    public static void main(String[] args) {
        RecommendRouteRepository repository = new RecommendRouteFileRepository();

        System.out.println(repository);

        repository.save(new RecommendRoute(2L, 2.0, 5, 6.0, makeEdges()));
        repository.save(new RecommendRoute(3L, 2.0, 5, 6.0, makeEdges()));
        System.out.println("Save done");


        Optional<RecommendRoute> recommendRoute1 = repository.findById(1L);
        Optional<RecommendRoute> recommendRoute2 = repository.findById(100L);
        System.out.println(recommendRoute1);
        System.out.println(recommendRoute2);

        repository.deleteById(18L);

    }

    private static List<Edge> makeEdges(){
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1L, 2L, 3L, 4.0, 5, 6, 7, 8.0, 9.0));
        edges.add(new Edge(2L, 2L, 3L, 4.0, 5, 6, 7, 8.0, 9.0));
        edges.add(new Edge(3L, 2L, 3L, 4.0, 5, 6, 7, 8.0, 9.0));
        edges.add(new Edge(4L, 2L, 3L, 4.0, 5, 6, 7, 8.0, 9.0));
        edges.add(new Edge(5L, 2L, 3L, 4.0, 5, 6, 7, 8.0, 9.0));

        return edges;
    }


}
