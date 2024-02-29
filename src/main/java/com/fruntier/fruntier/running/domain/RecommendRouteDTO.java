package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class RecommendRouteDTO {
    private Long id;
    private List<VertexDTO> vertexDTOList;

    public RecommendRouteDTO() {
    }

    public RecommendRouteDTO(Long id, List<VertexDTO> vertexDTOList) {
        this.id = id;
        this.vertexDTOList = vertexDTOList;
    }
}
