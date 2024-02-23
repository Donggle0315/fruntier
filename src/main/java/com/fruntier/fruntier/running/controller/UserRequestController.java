package com.fruntier.fruntier.running.controller;

import com.fruntier.fruntier.running.domain.*;
import com.fruntier.fruntier.running.repository.VertexRepository;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.running.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/running")
public class UserRequestController {
    private UserRequestService userRequestService;
    private RecommendRouteService recommendRouteService;
    private VertexRepository vertexRepository;


    @Autowired
    public UserRequestController(UserRequestService userRequestService, RecommendRouteService recommendRouteService) {
        this.userRequestService = userRequestService;
        this.recommendRouteService = recommendRouteService;
    }


    @GetMapping
    public String userRequest() {
        return "recommend/userRequest";
    }

    @ResponseBody
    @PostMapping("/recommendation")
    public ResponseEntity<RecommendRouteDTO> receiveVerticesJson(@RequestBody Map<String, Object> payload) {

        // Process the received JSON data on the server
        int expectedDistance = (int) payload.get("expectedDistance");
        Object vertices = payload.get("vertices");
        System.out.println("Received expectedDistance: " + expectedDistance);
        System.out.println("Received vertices: " + vertices);
        System.out.println("payload = " + payload);

        UserRequest userRequest = userRequestService.makeUserRequesetFromJSON(payload);
        List<Vertex> recommendRoute = recommendRouteService.makeRecommendRouteNormal(userRequest);

        // Convert Vertex objects to UserPoint objects for response
        List<UserPoint> userPointList = new ArrayList<>();
        List<Coordinate> coordinateList = new ArrayList<>();
        for (Vertex vertex : recommendRoute) {
            UserPoint userPoint = new UserPoint("", vertex.getCoordinate());
            userPointList.add(userPoint);
            Coordinate coordinate = new Coordinate(vertex.getCoordinate().getLongitude(), vertex.getCoordinate().getLatitude());
            coordinateList.add(coordinate);
        }

        RecommendRoute reco = new RecommendRoute();
        reco.setDistance((double) expectedDistance);
        reco.setScore(0.0);
        reco.setRouteVertices(recommendRoute);

        RecommendRoute result = recommendRouteService.saveRoute(reco);
        Long routeId;
        if (result == null) {
            routeId = 0L;
        } else{
            routeId = result.getId();
        }

        RecommendRouteDTO recommendRouteDTO = new RecommendRouteDTO(routeId, userPointList);

        // Set the recommended route in the response
        return ResponseEntity.ok(recommendRouteDTO);
    }
}
