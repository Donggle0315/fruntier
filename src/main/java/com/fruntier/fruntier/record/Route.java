package com.fruntier.fruntier.record;

import java.util.List;

public class Route {
    private Long userId;
    private Long routeId;
    private double distance;
    private double running_time;
    private int date_time;
    private List<RouteElement> routeElementList;

    public Route(Long userId, Long routeId, double distance, double running_time, int date_time, List<RouteElement> routeElementList) {
        this.userId = userId;
        this.routeId = routeId;
        this.distance = distance;
        this.running_time = running_time;
        this.date_time = date_time;
        this.routeElementList = routeElementList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getRunning_time() {
        return running_time;
    }

    public void setRunning_time(double running_time) {
        this.running_time = running_time;
    }

    public int getDate_time() {
        return date_time;
    }

    public void setDate_time(int date_time) {
        this.date_time = date_time;
    }

    public List<RouteElement> getRouteElementList() {
        return routeElementList;
    }

    public void setRouteElementList(List<RouteElement> routeElementList) {
        this.routeElementList = routeElementList;
    }
}
