package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.domain.RouteElementDTO;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.record.service.RouteSaveService;
import com.fruntier.fruntier.running.service.RecommendRouteService;
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

    @Autowired
    public RouteController(RouteSaveService routeSaveService,
                           RouteRetrieveService routeRetrieveService,
                           RecommendRouteService recommendRouteService) {
        this.routeSaveService = routeSaveService;
        this.routeRetrieveService = routeRetrieveService;
        this.recommendRouteService = recommendRouteService;
    }

    @GetMapping
    public String listRoutes(Model model) {
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
    public String saveRouteForm() {
        return "route/route-save";
    }

    @PostMapping("/save")
    public String saveRoute(@RequestBody Map<String, Object> param) {
        Long routeId = Long.valueOf((Integer) param.get("route"));
        String title = (String) param.get("title");

        Route route = makeRouteInstance(routeId, title);
        routeSaveService.save(route);

        return "redirect:recommend/userRequest";
    }

    private Route makeRouteInstance(Long routeId, String title) {
        Route route = new Route();
        route.setId(routeId);
        route.setRecommendRoute(recommendRouteService.findRecommendRouteById(routeId));
        route.setTitle(title);
        return route;
    }

    @GetMapping("/running/success")
    public String saveRecommendRouteSuccess() {
        return "save-success";
    }

    @GetMapping("/find")
    public String routeElementMapping(@RequestParam String routeId, Model model) {
        Long id = Long.parseLong(routeId);
        model.addAttribute(routeRetrieveService.getRouteFoundById(id));

        return "route/route-element";
    }
}
