package com.tao.insurance.service;

import com.tao.insurance.entity.StationDistanceVO;

import java.util.List;

public interface StationService {
    List<StationDistanceVO> getNearbyStations(double lon, double lat, double radius);
}
