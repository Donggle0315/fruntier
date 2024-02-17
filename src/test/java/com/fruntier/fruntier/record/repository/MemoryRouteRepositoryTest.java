//package com.fruntier.fruntier.record.repository;
//
//import com.fruntier.fruntier.record.domain.Route;
//import org.aspectj.lang.annotation.Before;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//class MemoryRouteRepositoryTest {
//    private RouteRepository routeRepository = new MemoryRouteRepository();
//
//    @BeforeEach
//    public void setUp() {
//        routeRepository = new MemoryRouteRepository();
//    }
//    @Test
//    public void saveRouteNormal() {
//        Route route = new Route();
//        route.setId(1L);
//        route.setUserId(1L);
//        route.setTitle("test");
//
//        Route result = routeRepository.save(route);
//
//        assertThat(result.getId()).isEqualTo(route.getId());
//        assertThat(result.getUserId()).isEqualTo(route.getUserId());
//        assertThat(result.getTitle()).isEqualTo(route.getTitle());
//    }
//
//    @Test
//    public void findById() {
//        Route test = routeRepository.save(new Route(1L, 1L, "test", null, null));
//
//        Route result = routeRepository.findById(test.getId());
//
//        assertThat(test).isEqualTo(result);
//    }
//
//    @Test
//    public void findByIdNotExistId() {
//        Long count = Long.valueOf(routeRepository.findAll().size());
//
//        assertThrows(NoSuchElementException.class, () -> routeRepository.findById(count * count));
//    }
//
//    @Test
//    public void findByIdElementNull() {
//        assertThrows(NoSuchElementException.class, () -> routeRepository.findById(null));
//    }
//
//    @Test
//    public void findAllNone() {
//        List<Route> routes = routeRepository.findAll();
//
//        assertThat(routes.size()).isEqualTo(0);
//    }
//
//    @Test
//    public void findAllHasElements() {
//        Route test1 = routeRepository.save(new Route(1L, 2L, "test1", null,null));
//        Route test2 = routeRepository.save(new Route(1L, 3L, "test2", null, null));
//        Route test3 = routeRepository.save(new Route(1L, 4L, "test3", null, null));
//
//        List<Route> routes = routeRepository.findAll();
//
//        for (Route route : routes) {
//            System.out.println("route = " + route.getId());
//            System.out.println("route = " + route.getUserId());
//            System.out.println("route = " + route.getTitle());
//        }
//        assertThat(routes.size()).isEqualTo(3);
//    }
//}