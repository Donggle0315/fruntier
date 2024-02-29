package com.fruntier.fruntier.running.controller;

import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.running.domain.*;
import com.fruntier.fruntier.running.exception.NotFindRecommendRouteException;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.running.service.UserRequestService;
import com.fruntier.fruntier.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/running")
public class UserRequestController {
    private final UserRequestService userRequestService;
    private final RecommendRouteService recommendRouteService;

    @Autowired
    public UserRequestController(UserRequestService userRequestService, RecommendRouteService recommendRouteService) {
        this.userRequestService = userRequestService;
        this.recommendRouteService = recommendRouteService;
    }


    @GetMapping
    public String userRequest() {
        return "recommend/user-request";
    }

    @ResponseBody
    @PostMapping("/recommendation")
    @RequireTokenValidation
    public Object receiveVerticesJson(@RequestBody UserRequestDTO userRequestDTO,
                                      HttpServletRequest request) {
        UserRequest userRequest = convertFromUserRequestDTOToUserRequest(userRequestDTO);
        User user = (User)request.getAttribute("validatedUser");

        try {
            List<Vertex> recommendRouteVertices = recommendRouteService.makeRecommendRouteNormal(userRequest);
            RecommendRoute recommendRoute = makeRecommendRouteInstance(userRequestDTO.getExpectedDistance(), recommendRouteVertices, user);
            recommendRouteService.saveRoute(recommendRoute);
            List<VertexDTO> vertexDTOList = convertVertexListToVertexDTOList(recommendRouteVertices);
            RecommendRouteDTO recommendRouteDTO = new RecommendRouteDTO(recommendRoute.getId(), vertexDTOList);
            return ResponseEntity.ok(recommendRouteDTO);
        } catch (NotFindRecommendRouteException | NoSuchElementException e) {
            return ResponseEntity.notFound();
        }
    }

    private static List<VertexDTO> convertVertexListToVertexDTOList(List<Vertex> recommendRoute) {
        List<VertexDTO> vertexDTOList = new ArrayList<>();
        for (Vertex vertex : recommendRoute) {
            VertexDTO vertexDTO = new VertexDTO("", vertex.getCoordinate());
            vertexDTOList.add(vertexDTO);
        }
        return vertexDTOList;
    }

    private UserRequest convertFromUserRequestDTOToUserRequest(UserRequestDTO userRequestDTO) {
        Coordinate startCoordinate = new Coordinate(userRequestDTO.getStartLat(), userRequestDTO.getStartLng());
        Coordinate endCoordinate = new Coordinate(userRequestDTO.getEndLat(), userRequestDTO.getEndLng());
        Vertex startVertex = userRequestService.convertCoordinateToVertex(startCoordinate);
        Vertex endVertex = userRequestService.convertCoordinateToVertex(endCoordinate);
        UserRequest userRequest = new UserRequest(startVertex, endVertex, userRequestDTO.getExpectedDistance());
        return userRequest;
    }

    private static RecommendRoute makeRecommendRouteInstance(double expectedDistance, List<Vertex> recommendRoute, User user) {
        RecommendRoute reco = new RecommendRoute();
        reco.setDistance(expectedDistance);
        reco.setScore(0.0);
        reco.setRouteVertices(recommendRoute);
        reco.setUserId(user.getId());
        return reco;
    }
}
