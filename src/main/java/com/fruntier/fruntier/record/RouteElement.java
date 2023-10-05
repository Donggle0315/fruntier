package com.fruntier.fruntier.record;

public class RouteElement {
    private Long routeId;
    private int order;
    private double x_coordinate;
    private double y_coordinate;
    private double arrived_time;

    public RouteElement(Long routeId, int order, double x_coordinate, double y_coordinate, double arrived_time) {
        this.routeId = routeId;
        this.order = order;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.arrived_time = arrived_time;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public double getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(double arrived_time) {
        this.arrived_time = arrived_time;
    }
}
