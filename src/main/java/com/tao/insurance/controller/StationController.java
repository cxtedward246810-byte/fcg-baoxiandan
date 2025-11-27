package com.tao.insurance.controller;

import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.entity.QualifiedStations;
import com.tao.insurance.entity.StationDistanceVO;
import com.tao.insurance.service.MakeTextService;
import com.tao.insurance.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/station")
public class StationController {

    @Autowired
    private StationService stationService;
@Autowired
private MakeTextService MService;
    @GetMapping("/nearby")
    public List<StationDistanceVO> getNearby(
            @RequestParam double lon,
            @RequestParam double lat,
            @RequestParam(defaultValue = "5") double radius) {

        return stationService.getNearbyStations(lon, lat, radius);
    }

    @PostMapping("/getQualifiedStations")
    public ApiResponse<Object> getQualifiedStations(@RequestBody QualifiedStations qq){


        return MService.returnStations(qq.getInsuranceType(),qq.getTimeRange());
    }
}
