package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.record.domain.Route;
import com.fruntier.fruntier.record.domain.RouteElementDTO;
import com.fruntier.fruntier.record.domain.SaveRouteDTO;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.record.service.RouteSaveService;
import com.fruntier.fruntier.running.service.RecommendRouteService;
import com.fruntier.fruntier.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


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
    @RequireTokenValidation
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

    /*
    TODO : 페이지가 아니라 alert로 바꿔서 나타내기
     */
    @GetMapping("/save")
    public String saveRouteForm() {
        return "route/route-save";
    }

    @PostMapping("/save")
    @RequireTokenValidation
    public String saveRoute(@RequestBody SaveRouteDTO saveRouteDTO,
                            HttpServletRequest request) {
        User user = (User) request.getAttribute("validatedUser");

        Route route = makeRouteInstance(user.getId(), saveRouteDTO.getRouteId(), saveRouteDTO.getTitle());
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
    @RequireTokenValidation
    public String routeElementMapping(@RequestParam String routeId,
                                      Model model) {
        Long id = Long.parseLong(routeId);
        try {
            model.addAttribute(routeRetrieveService.findRouteById(id));
        } catch (NoSuchElementException e) {
            return "redirect:/route";
        }

        return "route/route-element";
    }
}
