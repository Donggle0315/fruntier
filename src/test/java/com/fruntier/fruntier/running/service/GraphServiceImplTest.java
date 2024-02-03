//package com.fruntier.fruntier.running.service;
//
//import com.fruntier.fruntier.running.domain.Coordinate;
//import com.fruntier.fruntier.running.domain.Edge;
//import com.fruntier.fruntier.running.domain.Vertex;
//import com.fruntier.fruntier.running.repository.VertexMemoryRepository;
//import com.fruntier.fruntier.running.repository.VertexRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.sql.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//public class GraphServiceImplTest {
//    private final VertexRepository vertexRepository;
//    private final GraphService graphService;
//
//    @Autowired
//    public GraphServiceImplTest(VertexRepository vertexRepository, GraphService graphService) {
//        this.vertexRepository = vertexRepository;
//        this.graphService = graphService;
//    }
//
//    @Test
//    void findConnectedEdge(){
//        //fill up vertex and edge
//        List<Edge> v1Edge = new ArrayList<>();
//        List<Edge> v2Edge = new ArrayList<>();
//        List<Edge> v3Edge = new ArrayList<>();
//
//        Coordinate c1 = new Coordinate(10.0,10.0);
//        Coordinate c2 = new Coordinate(20.0,20.0);
//        Coordinate c3 = new Coordinate(30.0,40.0);
//
//
//        Vertex v1 = new Vertex(1L,c1,"대흥동",v1Edge);
//        Vertex v2 = new Vertex(2L,c2,"서강동",v2Edge);
//        Vertex v3 = new Vertex(2L,c3,"마포1동",v2Edge);
//
//
//        Edge edge1 = new Edge(1L,v1,v2,20.0,1,1,1,1.1,2.3);
//        Edge edge2 = new Edge(2L, v2,v1,20.0,1,1,1,1.1,2.3);
//        Edge edge3 = new Edge(3L,v2,v3,30.0,1,1,1,1.1,2.3);
//        Edge edge4 = new Edge(4L, v3,v2,50.0,1,1,1,1.1,3.3);
//
//        //vertex 1 and 2 , 2 and 3 are connected.
//
//        //add Edge list to v1
//        List<Edge> v1EdgeList = new ArrayList<>();
//        v1EdgeList.add(edge1);
//        v1.setOutEdge(v1EdgeList);
//
//        //add Edge list to v2
//        List<Edge> v2EdgeList = new ArrayList<>();
//        v2EdgeList.add(edge2);
//        v2EdgeList.add(edge3);
//        v2.setOutEdge(v2EdgeList);
//
//
//        //add Edge list to v3
//        List<Edge> v3EdgeList = new ArrayList<>();
//        v3EdgeList.add(edge4);
//        v3.setOutEdge(v3EdgeList);
//
//        //add the Vertices into DB
//        vertexRepository.save(v1);
//        vertexRepository.save(v2);
//        vertexRepository.save(v3);
//
//        List<Edge> resultEdge = graphService.findConnectedEdge(v2);
//
//        Assertions.assertThat(resultEdge).contains(edge2,edge3);
//
//
//    }
//
////    @AfterEach
////    void clear(){
////        vertexRepository.clear();
////    }
//}
