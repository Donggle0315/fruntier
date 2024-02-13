package com.fruntier.fruntier.record.domain;

import com.fruntier.fruntier.running.domain.RecommendRoute;
import jakarta.persistence.*;

import java.util.List;

//@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> history;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Record> getHistory() {
        return history;
    }

    public void setHistory(List<Record> history) {
        this.history = history;
    }

    public RecommendRoute getRecommendRoute() {
        return recommendRoute;
    }

    public void setRecommendRoute(RecommendRoute recommendRoute) {
        this.recommendRoute = recommendRoute;
    }
}
