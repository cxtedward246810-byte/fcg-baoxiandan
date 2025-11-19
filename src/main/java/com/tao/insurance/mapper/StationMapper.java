package com.tao.insurance.mapper;

import com.tao.insurance.entity.StationDistanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StationMapper {

    @Select(
            "SELECT  stationNum, stationName, lon, lat, " +
                    "       ( " +
                    "         6371 * acos( " +
                    "           cos(radians(#{lat})) * cos(radians(lat)) * " +
                    "           cos(radians(lon) - radians(#{lon})) + " +
                    "           sin(radians(#{lat})) * sin(radians(lat)) " +
                    "         ) " +
                    "       ) AS distance " +
                    "FROM station " +
                    "WHERE lat BETWEEN #{minLat} AND #{maxLat} " +
                    "  AND lon BETWEEN #{minLon} AND #{maxLon} " +
                    "HAVING distance <= #{radius} " +
                    "ORDER BY distance"
    )
    List<StationDistanceVO> findStations(
            @Param("lon") double lon,
            @Param("lat") double lat,
            @Param("radius") double radius,
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLon") double minLon,
            @Param("maxLon") double maxLon
    );
}
