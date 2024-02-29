package com.fruntier.fruntier.record.domain;

import com.fruntier.fruntier.running.domain.RecommendRoute;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> history = new ArrayList<>();
    @ManyToOne
    private RecommendRoute recommendRoute;

    public Route() {
    }

    public Route(Long id, Long userId, String title, List<Record> history, RecommendRoute recommendRoute) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.history = history;
        this.recommendRoute = recommendRoute;
    }
}
