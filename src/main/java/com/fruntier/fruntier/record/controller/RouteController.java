package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.domain.RouteElementDTO;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.record.service.RouteSaveService;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/route")
public class RouteController {
    private final RouteSaveService routeSaveService;
    private final RouteRetrieveService routeRetrieveService;
    private final RecommendRouteService recommendRouteService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public RouteController(RouteSaveService routeSaveService,
                           RouteRetrieveService routeRetrieveService,
                           RecommendRouteService recommendRouteService, JwtTokenService jwtTokenService) {
        this.routeSaveService = routeSaveService;
        this.routeRetrieveService = routeRetrieveService;
        this.recommendRouteService = recommendRouteService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping
    public String listRoutes(@CookieValue(value = "authToken", required = false) String authCookie,
                             Model model)
            throws UserNotLoggedInException {

        checkCookieValueExist(authCookie);

        List<Route> routeList = routeRetrieveService.listRoutesAllNormal();
        List<RouteElementDTO> routes = getRouteElementDTOList(routeList);
        model.addAttribute("routes", routes);

        return "route/routes-list";
    }

    private static List<RouteElementDTO> getRouteElementDTOList(List<Route> routeList) {
        List<RouteElementDTO> routes = new ArrayList<>();
        for (Route route : routeList) {
            RouteElementDTO elem = new RouteElementDTO(
                    route.getId(),
                    route.getUserId(),
                    route.getTitle()
            );

            routes.add(elem);
        }
        return routes;
    }

    @GetMapping("/save")
    public String saveRouteForm(@CookieValue(value = "authToken", required = false) String authCookie)
            throws UserNotLoggedInException {

        checkCookieValueExist(authCookie);
        return "route/route-save";
    }

    @PostMapping("/save")
    public String saveRoute(@RequestBody Map<String, Object> param,
                            @CookieValue(value = "authToken", required = false) String authCookie)
            throws UserNotLoggedInException {

        checkCookieValueExist(authCookie);

        Long routeId = Long.valueOf((Integer) param.get("route"));
        String title = (String) param.get("title");

        Long userId;
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            userId = user.getId();
        } catch (TokenValidationException e) {
            throw new UserNotLoggedInException("user not logged in");
        }

        Route route = makeRouteInstance(userId, routeId, title);
        routeSaveService.save(route);

        return "redirect:success";
    }

    private Route makeRouteInstance(Long userId, Long routeId, String title) {
        Route route = new Route();
        route.setUserId(userId);
        route.setId(routeId);
        route.setRecommendRoute(recommendRouteService.findRecommendRouteById(routeId));
        route.setTitle(title);
        return route;
    }

    @GetMapping("/success")
    public String saveRouteSuccess() {
        return "route/save-success";
    }

    @GetMapping("/find")
    public String routeElementMapping(@RequestParam String routeId,
                                      @CookieValue(value = "authToken", required = false) String autoCookie,
                                      Model model)
            throws UserNotLoggedInException {

        checkCookieValueExist(autoCookie);

        Long id = Long.parseLong(routeId);
        model.addAttribute(routeRetrieveService.getRouteFoundById(id));

        return "route/route-element";
    }

    private static void checkCookieValueExist(String authCookie) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
    }
}
