package com.tao.insurance.service.impl;

import com.tao.insurance.entity.StationDistanceVO;
import com.tao.insurance.mapper.StationMapper;
import com.tao.insurance.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationMapper stationMapper;

    @Override
    public List<StationDistanceVO> getNearbyStations(double lon, double lat, double radius) {

        // 1° 纬度 = 111 km
        double latDiff = radius / 111.0;

        // 1° 经度 ≈ 111 km × cos(lat)
        double lonDiff = radius / (111.0 * Math.cos(Math.toRadians(lat)));

        double minLat = lat - latDiff;
        double maxLat = lat + latDiff;

        double minLon = lon - lonDiff;
        double maxLon = lon + lonDiff;

        return stationMapper.findStations(
                lon, lat, radius,
                minLat, maxLat,
                minLon, maxLon
        );
    }
}
