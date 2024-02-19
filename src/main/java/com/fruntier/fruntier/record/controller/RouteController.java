package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.domain.RouteDTO;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.record.service.RouteSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/route")
public class RouteController {
    private final RouteSaveService routeSaveService;
    private final RouteRetrieveService routeRetrieveService;
    @Autowired
    public RouteController(RouteSaveService routeSaveService, RouteRetrieveService routeRetrieveService) {
        this.routeSaveService = routeSaveService;
        this.routeRetrieveService = routeRetrieveService;
    }

    @GetMapping("/save")
    public String saveRouteForm() {
        System.out.println("RouteController.saveRouteForm");
        return "route-save";
    }

    @PostMapping("/save")
    public String saveRoute(@RequestBody Map<String, Object> param) {
        Integer routeId = (Integer) param.get("route");
        String title = (String) param.get("title");
        System.out.println("RouteController.saveRoute");
        Route route = new Route();
        route.setId((long)routeId);
        route.setTitle(title);

        System.out.println("route = " + route.getTitle());
        System.out.println("routeId = " + routeId);
        routeSaveService.save(route);

        return "redirect:/";
    }

    @GetMapping
    @ResponseBody
    public List<Route> routeRetrieve() {
        System.out.println("RouteController.routeRetrieve");
        return routeRetrieveService.listRoutesAllNormal();
    }

    @GetMapping("/{routeId}")
    @ResponseBody
    public Route routeElementMapping(Long routeId) {
        System.out.println("RouteController.routeElementMapping");
        return routeRetrieveService.getRouteFoundById(routeId);
    }
}
