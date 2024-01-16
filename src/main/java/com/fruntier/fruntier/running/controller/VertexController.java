package com.fruntier.fruntier.running.controller;

import com.fruntier.fruntier.running.domain.*;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.running.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VertexController {
    private UserRequestService userRequestService;
    private RecommendRouteService recommendRouteService;

    private List<Vertex> tempVertexList;

    @Autowired
    public VertexController(UserRequestService userRequestService, RecommendRouteService recommendRouteService) {
        this.userRequestService = userRequestService;
        this.recommendRouteService = recommendRouteService;
    }

    @PostMapping("/send-vertices-json")
    public ResponseEntity<List<UserPoint>> receiveVerticesJson(@RequestBody Map<String, Object> payload) {
        // Process the received JSON data on the server
        int expectedDistance = (int) payload.get("expectedDistance");
        Object vertices = payload.get("vertices");
        System.out.println("Received expectedDistance: " + expectedDistance);
        System.out.println("Received vertices: " + vertices);

        UserRequest userRequest = userRequestService.makeUserRequesetFromJSON(payload);
        List<Vertex> recommendRoute = recommendRouteService.makeRecommendRouteNormal(userRequest);

        // Convert Vertex objects to UserPoint objects for response
        List<UserPoint> userPointList = new ArrayList<>();
        for (Vertex vertex : recommendRoute) {
            UserPoint userPoint = new UserPoint("", vertex.getCoordinate());
            userPointList.add(userPoint);
        }

        // Set the recommended route in the response
        return ResponseEntity.ok(userPointList);
    }

    @GetMapping("/get-data")
    public ResponseEntity<List<UserPoint>> getData() {
        if (tempVertexList == null || tempVertexList.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content to send
        }

        // Convert Vertex objects to UserPoint objects for response
        List<UserPoint> userPointList = new ArrayList<>();
        for (Vertex vertex : tempVertexList) {
            UserPoint userPoint = new UserPoint("", vertex.getCoordinate());
            userPointList.add(userPoint);
        }

        // Return the recommended route data
        return ResponseEntity.ok(userPointList);
    }
}

