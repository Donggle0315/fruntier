//package com.fruntier.fruntier.record.repository;
//
//import com.fruntier.fruntier.record.domain.Route;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@Component
//public class MemoryRouteRepository implements RouteRepository {
//    private Map<Long, Route> routeMap;
//    private Long sequence;
//
//    public MemoryRouteRepository() {
//        routeMap = new HashMap<>();
//        sequence = 0L;
//    }
//
//    @Override
//    public Route save(Route route) {
//        route.setId(++sequence);
//        routeMap.put(sequence, route);
//        return route;
//    }
//
//    @Override
//    public Route findById(Long id) {
//        Route route = routeMap.get(id);
//        if (route == null) {
//            throw new NoSuchElementException();
//        }
//        return route;
//    }
//
//    @Override
//    public List<Route> findAll() {
//        return routeMap.values().stream().toList();
//    }
//}
