package com.fruntier.fruntier.record;

import java.util.List;

public interface RouteRepository {
    /**
     * 루트를 저장소에 저장
     * @param route 저장할 루트 클래스
     */
    void saveRoute(Route route);

    /**
     * 루트를 저장소에서 삭제
     * @param routeId 삭제할 루트 아이디
     * @return 삭제 성공 여부
     */
    boolean deleteRoute(Long routeId);

    /**
     * 루트를 저장소에서 검색
     * @param routeId 검색할 루트 아이디
     * @return 루트 클래스
     */
    Route findRouteById(Long routeId);

    /**
     * 루트 요소를 저장소에 저장
     * @param routeElement 루트 원소 클래스
     */
    void saveRouteElement(RouteElement routeElement);

    /**
     * 루트 원소를 저장소에서 삭제
     * @param routeID 루트 아이디
     * @param order 원소 순서
     * @return 삭제 성공 여부
     */
    boolean deleteRouteElement(Long routeID, int order);

    /**
     * 루트의 모든 루트 원소를 검색
     * @param routeId 루트 아이디
     * @return 루트 원소를 담은 리스트
     */
    List<RouteElement> findByRouteId(Long routeId);
}
