package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter @Setter @ToString
public class UserRequestDTO {
    private Integer expectedDistance;
    private Double startLat;
    private Double startLng;
    private Double endLat;
    private Double endLng;
}
