package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


public interface EdgeRepository extends JpaRepository<Edge, Long> {
}
