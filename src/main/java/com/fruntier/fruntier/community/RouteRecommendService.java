package com.fruntier.fruntier.community;

public interface RouteRecommendService {
    /**
     * 루트 추천을 위한 추가적인 정보 등록
     * @param routeId 루트 아이디
     * @param content 추가적인 정보
     */
    void setRecommendContent(Long routeId, String content);

}
