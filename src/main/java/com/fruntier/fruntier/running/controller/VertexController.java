package com.fruntier.fruntier.running.controller;

import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.UserPoint;
import com.fruntier.fruntier.running.domain.UserRequest;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.running.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VertexController {
    private UserRequestService userRequestService;
    private RecommendRouteService recommendRouteService;

    @Autowired
    public VertexController(UserRequestService userRequestService, RecommendRouteService recommendRouteService) {
        this.userRequestService = userRequestService;
        this.recommendRouteService = recommendRouteService;
    }

    @PostMapping("/send-vertices-json")
    public ResponseEntity<String> receiveVerticesJson(@RequestBody Map<String, Object> payload) {
        // Process the received JSON data on server
        int expectedDistance = (int) payload.get("expectedDistance");
        Object vertices = payload.get("vertices");
        System.out.println("Received expectedDistance: " + expectedDistance);
        System.out.println("Received vertices: " + vertices);

        UserRequest userRequest = userRequestService.makeUserRequesetFromJSON(payload);
        //List<Vertex> recommmendRoute = recommendRouteService.makeRecommendRouteNormal(userRequest);

        String response = "{\"status\": \"success\", \"message\": \"Vertices received successfully\"}";

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-data")
    public List<UserPoint> getDate() {
        //return recommendRoute;
        return null;
    }
}
