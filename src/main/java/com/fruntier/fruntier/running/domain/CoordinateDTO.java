package com.fruntier.fruntier.running.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter @ToString
public class CoordinateDTO {
    private String title;
    private Map<String, Map<String, Double>> latLng;
}
