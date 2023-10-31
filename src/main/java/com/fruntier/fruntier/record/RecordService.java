package com.fruntier.fruntier.record;

import java.util.List;

public interface RecordService {
    /**
     * 루트를 저장하는 기능
     * @param route 생성할 루트 인스턴스
     */
    void createRoute(Route route);

    /**
     * 루트를 검색하는 기능
     * @param routeId 루트 아이디
     * @return 루트 인스턴스
     */
    Route findRouteById(Long routeId);

    /**
     * 해당 유저가 기록한 루트를 검색하는 기능
     * @param userId 유저 아이디
     * @return 루트 인스턴스를 담은 리스트
     */
    List<Route> findRouteByUserId(Long userId);

    /**
     * 해당 기간 동안 기록한 루트를 검색하는 기능
     * @param userId 검색할 유저 아이디, -1이면 전체 검색
     * @param start_date [첫 날
     * @param end_date 마지막 날]
     * @return 루트 인스턴스를 담은 리스트
     */
    List<Route> findRouteByDate(Long userId, int start_date, int end_date);

    /**
     * 해당 거리 사이만큼 기록한 루트를 검색하는 기능
     * @param userId 검색할 유저 아이디, -1이면 전체 검색
     * @param start_distance [최소 거리
     * @param end_distance 최대 거리]
     * @return 루트 인스턴스를 담은 리스트
     */
    List<Route> findRouteByDistance(Long userId, double start_distance, double end_distance);
}
