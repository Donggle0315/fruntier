package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.Vertex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class EdgeMemoryRepositoryTest {

     EdgeRepository edgeRepository;

    @Autowired
    public EdgeMemoryRepositoryTest(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    @Test
    void save(){
        List<Edge> v1Edge = new ArrayList<>();
        List<Edge> v2Edge = new ArrayList<>();
        Coordinate c1 = new Coordinate(10.0,10.0);
        Coordinate c2 = new Coordinate(20.0,20.0);
        Vertex v1 = new Vertex(1L,c1,"대흥동",v1Edge);
        Vertex v2 = new Vertex(2L,c2,"서강동",v2Edge);

        Edge edge1 = new Edge(1L,v1,v2,20.0,1,1,1,1.1,2.3);
        Edge nullEdge = null;

        //test1 : save edge 1
        edgeRepository.save(edge1);
        Optional<Edge> resultEdge = edgeRepository.findById(edge1.getId());
        if(resultEdge.isPresent()){
            Assertions.assertThat(resultEdge.get()).isEqualTo(edge1);
        }else{
            Assertions.fail("Failed to save edge in repository");
        }

        //test2 : save nulledge
        try{
            edgeRepository.save(nullEdge);
            Assertions.fail("did not return exception as expected");
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getClass()).isEqualTo(IllegalArgumentException.class);
        }

    }
    @Test
    void findById(){
        List<Edge> v1Edge = new ArrayList<>();
        List<Edge> v2Edge = new ArrayList<>();
        Coordinate c1 = new Coordinate(10.0,10.0);
        Coordinate c2 = new Coordinate(20.0,20.0);
        Vertex v1 = new Vertex(1L,c1,"대흥동",v1Edge);
        Vertex v2 = new Vertex(2L,c2,"서강동",v2Edge);
        Edge edge1 = new Edge(1L,v1,v2,20.0,1,1,1,1.1,2.3);
        Edge edge2 = new Edge(2L,v1,v2,20.0,1,1,1,1.1,2.3);

        edgeRepository.save(edge1);
        edgeRepository.save(edge2);

        //test1 : find edge1
        Optional<Edge> resultEdge = edgeRepository.findById(1L);
        if(resultEdge.isPresent()){
            Assertions.assertThat(edge1).isEqualTo(resultEdge.get());
        }else{
            Assertions.fail("Edge not found in repository");
        }

        //test2 : find null
        try{
            Optional<Edge> noedge = edgeRepository.findById(null);
            Assertions.fail("did not return exception as expected");
        }catch (IllegalArgumentException e){
            Assertions.assertThat(edge1).isEqualTo(resultEdge.get());
        }

    }

    @Test
    void delete(){
        List<Edge> v1Edge = new ArrayList<>();
        List<Edge> v2Edge = new ArrayList<>();
        Coordinate c1 = new Coordinate(10.0,10.0);
        Coordinate c2 = new Coordinate(20.0,20.0);
        Vertex v1 = new Vertex(1L,c1,"대흥동",v1Edge);
        Vertex v2 = new Vertex(2L,c2,"서강동",v2Edge);

        Edge edge1 = new Edge(1L,v1,v2,20.0,1,1,1,1.1,2.3);
        Edge edge2 = new Edge(2L,v1,v2,20.0,1,1,1,1.1,2.3);

        edgeRepository.save(edge1);
        edgeRepository.save(edge2);

        edgeRepository.delete(edge1.getId());
        if(edgeRepository.findById(edge1.getId()).isPresent()){
            Assertions.fail("deletion failed");
        }


    }

//    @AfterEach
//    void clear(){
//        edgeRepository.clear();
//    }
}
