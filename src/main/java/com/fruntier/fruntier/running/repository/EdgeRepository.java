package com.fruntier.fruntier.running.repository;

import com.fruntier.fruntier.running.domain.Edge;
import org.springframework.stereotype.Repository;


import java.util.Optional;


public interface EdgeRepository {

    /**
     * 길을 저장소에 저장
     * @param edge 길 인스턴스
     * @return 길
     */
    Edge save(Edge edge);

    /**
     * 길을 저장소에서 검색
     * @param edgeId 길 아이디
     * @return Edge의 옵셔널
     */
    Optional<Edge> findById(Long edgeId);

    /**
     * 길을 저장소에서 삭제
     * @param edgeId 길 아이디
     */
    void delete(Long edgeId);
}
