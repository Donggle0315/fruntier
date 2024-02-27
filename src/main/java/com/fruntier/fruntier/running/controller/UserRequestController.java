package com.fruntier.fruntier.running.controller;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.running.domain.*;
import com.fruntier.fruntier.running.exception.NotFindRecommendRouteException;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.running.service.UserRequestService;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
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
    private final UserRequestService userRequestService;
    private final RecommendRouteService recommendRouteService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserRequestController(UserRequestService userRequestService, RecommendRouteService recommendRouteService, JwtTokenService jwtTokenService) {
        this.userRequestService = userRequestService;
        this.recommendRouteService = recommendRouteService;
        this.jwtTokenService = jwtTokenService;
    }


    @GetMapping
    public String userRequest(@CookieValue(value = "authToken", required = false) String authCookie)
            throws UserNotLoggedInException {
        checkCookieValueExist(authCookie);

        return "recommend/userRequest";
    }

    @ResponseBody
    @PostMapping("/recommendation")
    public Object receiveVerticesJson(@RequestBody Map<String, Object> payload,
                                      @CookieValue(value = "authToken", required = false) String authCookie)
            throws UserNotLoggedInException {

        checkCookieValueExist(authCookie);
        // Process the received JSON data on the server
        int expectedDistance = (int) payload.get("expectedDistance");
        Object vertices = payload.get("vertices");


        UserRequest userRequest = userRequestService.makeUserRequesetFromJSON(payload);
        List<Vertex> recommendRoute = null;
        try {
            recommendRoute = recommendRouteService.makeRecommendRouteNormal(userRequest);
        } catch (NotFindRecommendRouteException e) {
            System.out.println("here?");
            return ResponseEntity.notFound();
        }

        // Convert Vertex objects to UserPoint objects for response
        List<UserPoint> userPointList = new ArrayList<>();
        List<Coordinate> coordinateList = new ArrayList<>();
        for (Vertex vertex : recommendRoute) {
            UserPoint userPoint = new UserPoint("", vertex.getCoordinate());
            userPointList.add(userPoint);
            Coordinate coordinate = new Coordinate(vertex.getCoordinate().getLongitude(), vertex.getCoordinate().getLatitude());
            coordinateList.add(coordinate);
        }

        RecommendRoute reco = makeRecommendRouteInstance((double) expectedDistance, recommendRoute);

        try {
            reco.setUserId(getUserIdFromCookie(authCookie));
        } catch (TokenValidationException e) {
            throw new UserNotLoggedInException("user not logged in");
        }

        RecommendRoute result = recommendRouteService.saveRoute(reco);
        Long routeId;
        if (result == null) {
            routeId = 0L;
        } else {
            routeId = result.getId();
        }

        RecommendRouteDTO recommendRouteDTO = new RecommendRouteDTO(routeId, userPointList);

        // Set the recommended route in the response
        return ResponseEntity.ok(recommendRouteDTO);
    }

    private Long getUserIdFromCookie(String authCookie) throws TokenValidationException {
        User user = jwtTokenService.validateTokenReturnUser(authCookie);
        Long userId = user.getId();
        return userId;
    }

    private static RecommendRoute makeRecommendRouteInstance(double expectedDistance, List<Vertex> recommendRoute) {
        RecommendRoute reco = new RecommendRoute();
        reco.setDistance(expectedDistance);
        reco.setScore(0.0);
        reco.setRouteVertices(recommendRoute);
        return reco;
    }

    private static void checkCookieValueExist(String authCookie) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
    }
}
