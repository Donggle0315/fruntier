package com.fruntier.fruntier.running;

public interface EdgeRepository {

    /**
     * 길을 저장소에 저장
     * @param edge 길 인스턴스
     */
    void saveEdge(Edge edge);

    /**
     * 길을 저장소에서 검색
     * @param edgeId 길 아이디
     * @return 길 인스턴스
     */
    Edge findEdgeById(Long edgeId);

    /**
     * 길을 저장소에서 삭제
     * @param edgeId 길 아이디
     * @return 삭제 성공 여부
     */
    boolean deleteEdge(Long edgeId);
}
