package com.fruntier.fruntier.running;

import java.util.List;
public interface GraphService {

    /**
     * 연결된 엣지를 찾아온다
     * @param vertex 기준이 되는 vertex
     * @return 연결되어 있는 edge를 저장한 list
     */
    List<Edge> findConnectedEdge(Vertex vertex);
}
