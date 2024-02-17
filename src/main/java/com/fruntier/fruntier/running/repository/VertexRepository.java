package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Coordinate;
import com.fruntier.fruntier.running.domain.Edge;
import com.fruntier.fruntier.running.domain.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface VertexRepository extends JpaRepository<Vertex, Long> {
    Vertex findByCoordinate(Coordinate coordinate);
}
