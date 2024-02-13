    package com.fruntier.fruntier.record.controller;

    import com.fruntier.fruntier.record.domain.Route;
    import com.fruntier.fruntier.record.repository.RouteRepository;
    import com.fruntier.fruntier.record.service.RecordRetrieveService;
    import com.fruntier.fruntier.record.service.RecordSaveService;
    import com.fruntier.fruntier.record.service.RecordUpdateService;
    import com.fruntier.fruntier.running.domain.RecommendRoute;
    import com.fruntier.fruntier.running.domain.UserPoint;
    import com.fruntier.fruntier.running.domain.Vertex;
    import com.fruntier.fruntier.running.repository.RecommendRouteRepository;
    import jakarta.persistence.Id;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Controller
    @RequestMapping("/record")
    public class RouteController {
        private final RouteRepository routeRepository;
        private final RecordRetrieveService recordRetrieveService;
        private final RecordSaveService recordSaveService;
        private final RecordUpdateService recordUpdateService;
        private final RecommendRouteRepository recommendRouteRepository;

        @Autowired
        public RouteController(RouteRepository routeRepository, RecordRetrieveService recordRetrieveService, RecordSaveService recordSaveService, RecordUpdateService recordUpdateService, RecommendRouteRepository recommendRouteRepository) {
            this.routeRepository = routeRepository;
            this.recordRetrieveService = recordRetrieveService;
            this.recordSaveService = recordSaveService;
            this.recordUpdateService = recordUpdateService;
            this.recommendRouteRepository = recommendRouteRepository;
        }

        @GetMapping()
        public String recordMainPage() {
            return "record";
        }

        @PostMapping("/save")
        public ResponseEntity<Integer> routeSave(@RequestBody Map<String, Object> param) {
            System.out.println("RouteController.routeSave");
            for (Map.Entry<String, Object> stringObjectEntry : param.entrySet()) {
                System.out.println("stringObjectEntry = " + stringObjectEntry);
            }

            RecommendRoute recommendRoute = convertParamToRecommendRoute(param.get("routes"));

            Route route = new Route();
            route.setId(0L);
            route.setUserId(0L);
            route.setTitle(String.valueOf(param.get("title")));
            route.setHistory(null);
            route.setRecommendRoute(recommendRoute);

            recordSaveService.save(route);
            return ResponseEntity.ok(1);
        }

        private RecommendRoute convertParamToRecommendRoute(Object routes) {
            RecommendRoute recommendRoute = new RecommendRoute();
            System.out.println("routes = " + routes);


            //List<Vertex> vertices = new ArrayList<>();
                
            return recommendRoute;

        }

        @ResponseBody
        @GetMapping("/list/{sortMethod}")
        public List<Route> routeList(@PathVariable String sortMethod) {
            System.out.println("RouteController.routeList");
            List<Route> routeList = recordRetrieveService.listRoutesAllNormal(0);
            System.out.println("size : " + routeList.size());
            for (Route route : routeList) {
                System.out.println("route = " + route);
            }
            if (routeList == null) {
                return new ArrayList<Route>();
            }
            return routeList;
        }

        @ResponseBody
        @GetMapping("/search/{id}")
        public Route routeFind(@PathVariable String id) {
            System.out.println("RouteController.routeFind");
            Route route = recordRetrieveService.getRouteById(Long.parseLong(id));
            System.out.println("route = " + route);
            return route;
        }
    }
