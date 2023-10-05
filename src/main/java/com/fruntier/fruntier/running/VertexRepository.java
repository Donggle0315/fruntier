package com.fruntier.fruntier.running;

public interface VertexRepository {
    /**
     * 지점을 저장소에 저장
     * @param vertex 지점 인스턴스
     */
    void saveVertex(Vertex vertex);

    /**
     * 지점 아이디를 저장소에서 검색
     * @param vertexId 검색할 지점 아이디
     * @return 지점 인스턴스
     */
    Vertex findVertexById(Long vertexId);

    /**
     * 지점을 저장소에서 삭제
     * @param vertexId 삭제할 지점 아이디
     * @return 삭제 성공 여부
     */
    boolean deleteVertex(Long vertexId);
}
