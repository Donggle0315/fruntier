//package com.fruntier.fruntier.running.repository;
//
//import com.fruntier.fruntier.running.domain.Edge;
//import com.fruntier.fruntier.running.domain.RecommendRoute;
//import com.fruntier.fruntier.running.domain.Vertex;
//import org.springframework.stereotype.Repository;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.nio.file.StandardOpenOption;
//import java.util.*;
//import java.util.function.Predicate;
//
//@Repository
//public class RecommendRouteFileRepository implements RecommendRouteRepository {
//    private static final Path file_path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/RecommendRoute.txt");
//    private ArrayList<RecommendRoute> recommendRouteArrayList;
//    private Long id;
//
////    public RecommendRouteFileRepository() throws IllegalArgumentException {
////        recommendRouteArrayList = new ArrayList<>();
////        try {
////            Files.lines(file_path).forEach(line -> recommendRouteArrayList.add(convertStringToRecommendRoute(line)));
////        } catch (Exception e) {
////            throw new IllegalArgumentException();
////        }
////
////        id = recommendRouteArrayList.isEmpty() ? 0 : recommendRouteArrayList.get(recommendRouteArrayList.size() - 1).getId();
////    }
//
//    private RecommendRoute convertStringToRecommendRoute(String line) throws IllegalArgumentException {
//        ArrayList<String> parsed_line = new ArrayList<>(Arrays.asList(line.split(" ")));
//        parsed_line.removeIf(Predicate.isEqual(""));
//
//        try {
//            Long id = Long.parseLong(parsed_line.get(0));
//            Double distance = Double.parseDouble(parsed_line.get(1));
//            Integer expected_time = Integer.parseInt(parsed_line.get(2));
//            Double score = Double.parseDouble(parsed_line.get(3));
//            ArrayList<Edge> edges = convertStringToEdges(parsed_line.subList(4, parsed_line.size()));
//
//            return new RecommendRoute(id, distance, expected_time, score, edges);
//        } catch (Exception e) {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    private ArrayList<Edge> convertStringToEdges(List<String> line) throws IllegalArgumentException {
//        ArrayList<Edge> edges = new ArrayList<>();
//        VertexRepository vertexRepository = new VertexRepository();
//        try {
//            for (int i = 0; i < line.size(); i += 9) {
//                Long id = Long.parseLong(line.get(i));
////                Long start_vertex_id = Long.parseLong(line.get(i + 1));
////                Long end_vertex_id = Long.parseLong(line.get(i + 2));
//                Vertex start_vertex = vertexRepository.findById(Long.parseLong(line.get(i + 3))).get();
//                Vertex end_vertex = vertexRepository.findById(Long.parseLong(line.get(i + 4))).get();
//                Double distance = Double.parseDouble(line.get(i + 3));
//                Integer slope = Integer.parseInt(line.get(i + 4));
//                Integer width = Integer.parseInt(line.get(i + 5));
//                Integer population = Integer.parseInt(line.get(i + 6));
//                Double subjective_score = Double.parseDouble(line.get(i + 7));
//                Double total_score = Double.parseDouble(line.get(i + 8));
//
//                edges.add(new Edge(id, start_vertex, end_vertex, distance, slope, width, population, subjective_score, total_score));
//            }
//
//            return edges;
//        } catch (Exception e) {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    @Override
//    public RecommendRoute save(RecommendRoute recommendRoute) throws RuntimeException {
//        if (recommendRouteArrayList.contains(recommendRoute)) {
//            System.out.println("Reject to Save Duplicated Instance");
//            throw new IllegalArgumentException();
//        }
//
//        recommendRoute.setId(++this.id);
//        recommendRouteArrayList.add(recommendRoute);
//
//        fileWriteRecommendRouteEntries();
//        System.out.println("Save RecommendRoute (id : " + recommendRoute.getId() + ")");
//
//        return recommendRoute;
//    }
//
//    @Override
//    public Optional<RecommendRoute> findById(Long id) {
//        for (RecommendRoute recommendRoute : recommendRouteArrayList) {
//            if (recommendRoute.getId().equals(id)) {
//                return Optional.of(recommendRoute);
//            }
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public void delete(RecommendRoute recommendRoute) throws RuntimeException {
//        if (recommendRouteArrayList.contains(recommendRoute)) {
//            recommendRouteArrayList.remove(recommendRoute);
//            System.out.println("Remove recommendRoute (id : " + recommendRoute.getId() + ")");
//            fileWriteRecommendRouteEntries();
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) throws RuntimeException {
//        if (this.findById(id).isPresent()) {
//            delete(this.findById(id).get());
//            System.out.println("DeleteById id : " + id);
//        }
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//        for (RecommendRoute recommendRoute : recommendRouteArrayList) {
//            result.append(recommendRoute.toString()).append("\n");
//        }
//        return result.toString();
//    }
//
//    private void fileWriteRecommendRouteEntries() throws RuntimeException {
//        try {
//            Files.delete(file_path);
//            Files.createFile(file_path);
//            Files.writeString(file_path, recommendRouteArrayList.get(0).toString() + System.lineSeparator(), StandardOpenOption.WRITE);
//            for (RecommendRoute recommendRoute : recommendRouteArrayList.subList(1, recommendRouteArrayList.size())) {
//                Files.writeString(file_path, recommendRoute.toString() + System.lineSeparator(), StandardOpenOption.APPEND);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
