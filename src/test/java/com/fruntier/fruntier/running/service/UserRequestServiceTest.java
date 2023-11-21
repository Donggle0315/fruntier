package com.fruntier.fruntier.running.service;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.Vertex;
import com.fruntier.fruntier.running.repository.VertexMemoryRepository;
import com.fruntier.fruntier.running.repository.VertexRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class UserRequestServiceTest {
    private final VertexMemoryRepository vertexRepository;
    private final UserRequestService userRequestService;

    @Autowired
    public UserRequestServiceTest(VertexMemoryRepository vertexRepository, UserRequestService userRequestService) {
        this.vertexRepository = vertexRepository;
        this.userRequestService = userRequestService;
    }

    @BeforeEach
    void beforeTest(){
        // insertion code for vertex
        Coordinate c1 = new Coordinate(131D, 132D);
        Coordinate c2 = new Coordinate(133D, 135D);
        Coordinate c3 = new Coordinate(135D, 137D);
        Vertex v1 = new Vertex(0L, c1, null, null);
        Vertex v2 = new Vertex(1L, c2, null, null);
        Vertex v3 = new Vertex(2L, c3, null, null);
        vertexRepository.save(v1);
        vertexRepository.save(v2);
        vertexRepository.save(v3);
    }

    @AfterEach
    void afterTest(){
        vertexRepository.clear();
    }



    @Test
    void convertCoordinateToVertexTest(){
        // given
        Coordinate c1 = new Coordinate(131.5D, 131.5D);

        // when
        Vertex v0_0 = userRequestService.convertCoordinateToVertex(c1);
        Vertex v0_1 = vertexRepository.findById(0L).get();

        // then
        assertThat(v0_0).isEqualTo(v0_1);
    }
}
