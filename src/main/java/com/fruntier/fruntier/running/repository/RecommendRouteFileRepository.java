package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.RecommendRoute;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;

public class RecommendRouteFileRepository implements RecommendRouteRepository {
    static final Path file_path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/RecommendRoute.txt");
    private ArrayList<RecommendRoute> recommendRouteArrayList;
    private Long id;

    public RecommendRouteFileRepository() throws IllegalArgumentException {
        recommendRouteArrayList = new ArrayList<>();
        try {
            Files.lines(file_path)
                    .forEach(line -> recommendRouteArrayList.add(convertStringToRecommendRoute(line)));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

        id = (long) recommendRouteArrayList.size();
    }

    private RecommendRoute convertStringToRecommendRoute(String line) throws IllegalArgumentException {
        ArrayList<String> parsed_line = new ArrayList<>(Arrays.asList(line.split(" ")));
        parsed_line.removeIf(Predicate.isEqual(""));

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
    public RecommendRoute save(RecommendRoute recommendRoute) throws IllegalArgumentException {
        if (recommendRouteArrayList.contains(recommendRoute)) {
            System.out.println("Reject to Save Duplicated Instance");
            throw new IllegalArgumentException();
        }

        recommendRoute.setId(++this.id);
        recommendRouteArrayList.add(recommendRoute);

        try {
            //Files.writeString(file_path, convertRecommendRouteToString(recommendRoute));
            Files.write(file_path, (convertRecommendRouteToString(recommendRoute) + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Save RecommendRoute (id : " + recommendRoute.getId() + ")");

        return recommendRoute;
    }

    private String convertRecommendRouteToString(RecommendRoute recommendRoute) {
        return recommendRoute.toString() + convertEdgesToString(recommendRoute.getRoute_edges());
    }

    private String convertEdgesToString(List<Edge> edges) {
        String result = "";
        for (Edge edge : edges) {
            result += edge.toString() + " ";
        }

        return result;
    }

    @Override
    public Optional<RecommendRoute> findById(Long id) {
        for (RecommendRoute recommendRoute : recommendRouteArrayList) {
            if (recommendRoute.getId().equals(id)) {
                return Optional.of(recommendRoute);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(RecommendRoute recommendRoute) {
        recommendRouteArrayList.remove(recommendRoute);
        System.out.println("Remove recommendRoute (id : " + recommendRoute.getId() + ")");
    }

    @Override
    public void deleteById(Long id) {
        for (RecommendRoute recommendRoute : this.recommendRouteArrayList) {
            System.out.println(recommendRoute);
            if (recommendRoute.getId().equals(id)) {
                delete(recommendRoute);
                break;
            }
        }

        fileWriteRecommendRouteEntries();
    }

    @Override
    public String toString(){
        String result = "";
        for (RecommendRoute recommendRoute : recommendRouteArrayList) {
            result += recommendRoute.toString() + "\n";
        }

        return result;
    }

    private void fileWriteRecommendRouteEntries() {
        try {
            //Files.writeString(file_path, convertRecommendRouteToString(recommendRoute));
            Files.write(file_path, (convertRecommendRouteToString(recommendRouteArrayList.get(0)) + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
            for (RecommendRoute recommendRoute : recommendRouteArrayList.subList(1, recommendRouteArrayList.size())) {
                Files.write(file_path, (convertRecommendRouteToString(recommendRoute) + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

