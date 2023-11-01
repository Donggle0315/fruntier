package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RecommendRouteFileRepository implements RecommendRouteRepository{
    private ArrayList<RecommendRoute> recommendRouteArrayList;
    private Long id;

    public RecommendRouteFileRepository() throws IllegalArgumentException {
        recommendRouteArrayList = new ArrayList<>();

        try {
            String file_path = System.getProperty("user.dir") + "/src/main/resources//RecommendRoute.txt";
            Files.lines(Paths.get(file_path))
                    .forEach(line -> recommendRouteArrayList.add(convertStringToRecommendRoute(line)));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

        id = (long) recommendRouteArrayList.size();
    }

    private RecommendRoute convertStringToRecommendRoute(String line) throws IllegalArgumentException {
        System.out.println("convertStringToEdges()");
        ArrayList<String> parsed_line = new ArrayList<>(Arrays.asList(line.split(" ")));

        try {
            Long id = Long.parseLong(parsed_line.get(0));
            Double distance = Double.parseDouble(parsed_line.get(1));
            Integer expected_time = Integer.parseInt(parsed_line.get(2));
            Double score = Double.parseDouble(parsed_line.get(3));
            ArrayList<Edge> edges = convertStringToEdges(parsed_line.subList(4, parsed_line.size()));

            return new RecommendRoute(id, distance, expected_time, score, edges);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private ArrayList<Edge> convertStringToEdges(List<String> line) {
        ArrayList<Edge> edges = new ArrayList<>();
        System.out.println("converStringToEdges");

        try {
            for (int i = 0; i < line.size(); i += 9) {
                Long id = Long.parseLong(line.get(i));
                Long start_vertex_id = Long.parseLong(line.get(i + 1));
                Long end_vertex_id = Long.parseLong(line.get(i + 2));
                Double distance = Double.parseDouble(line.get(i + 3));
                Integer slope = Integer.parseInt(line.get(i + 4));
                Integer width = Integer.parseInt(line.get(i + 5));
                Integer population = Integer.parseInt(line.get(i + 6));
                Double subjective_score = Double.parseDouble(line.get(i + 7));
                Double total_score = Double.parseDouble(line.get(i + 8));

                edges.add(new Edge(id, start_vertex_id, end_vertex_id, distance, slope, width, population, subjective_score, total_score));
            }

            return edges;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void save(RecommendRoute recommendRoute) throws IllegalArgumentException {
        if (recommendRouteArrayList.contains(recommendRoute)) {
            System.out.println("Reject to Save Duplicated Instance");
            throw new IllegalArgumentException();
        }

        recommendRoute.setId(this.id++);
        recommendRouteArrayList.add(recommendRoute);

        System.out.println("Save RecommendRoute (id : " + recommendRoute.getId() + ")");
    }

    @Override
    public Optional<RecommendRoute> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(RecommendRoute recommendRoute) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
