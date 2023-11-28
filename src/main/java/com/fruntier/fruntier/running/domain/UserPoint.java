package com.fruntier.fruntier.running.domain;

public class UserPoint {
    private String title;
    private Coordinate coordinate;

    public UserPoint(String title, Coordinate coordinate) {
        this.title = title;
        this.coordinate = coordinate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "UserPoint{" +
                "title='" + title + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }
}
