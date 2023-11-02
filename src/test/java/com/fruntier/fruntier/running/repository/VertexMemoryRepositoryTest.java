package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.Vertex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VertexMemoryRepositoryTest {
    private final VertexMemoryRepository vertexRepository = new VertexMemoryRepository();

    @Test
    void save(){

        List<Edge> v1Edge = new ArrayList<>();
        List<Edge> v2Edge = new ArrayList<>();
        Coordinate c1 = new Coordinate(10.0,20.0);
        Coordinate c2 = new Coordinate(30.0,20.0);
        Vertex v1 = new Vertex(1L,c1,"대흥동",v1Edge);
        Vertex v2 = new Vertex(2L,c1,"대흥동",v2Edge);

        //test 1 : save
        vertexRepository.save(v1);
        Optional<Vertex> resultVertex = vertexRepository.findById(v1.getId());
        if(resultVertex.isPresent()){
            Assertions.assertThat(v1).isEqualTo(resultVertex.get());
        }else{
            Assertions.fail("Save failed");
        }

        //test 2 : exception

        try {
            vertexRepository.save(null);
            Assertions.fail("did not return expected exception");
        }catch(IllegalArgumentException e){
            Assertions.assertThat(IllegalArgumentException.class).isEqualTo(e.getClass());
        }
    }
}