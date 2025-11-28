package com.tao.insurance.entity;

import lombok.Data;

@Data
public class StationDistanceVO {
    private String stationNum;
    private String stationName;
    private Double lon;
    private Double lat;
    private Double distance; // 距离（公里）
    private Double value;
    private String dateTime;
}
