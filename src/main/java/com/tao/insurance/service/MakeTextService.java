package com.tao.insurance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.common.DisasterType;
import com.tao.insurance.common.InsuranceType;
import com.tao.insurance.dto.InsuranceInput;
import com.tao.insurance.engine.InsuranceCalculationEngine;
import com.tao.insurance.entity.PolicyQueryRequest;
import com.tao.insurance.entity.StationDistanceVO;
import com.tao.insurance.mapper.StationMapper;
import com.tao.service.CMADAASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MakeTextService {

    @Autowired
    private  InsuranceCalculationEngine engine;
@Autowired
private StationMapper stationMapper;
    public ApiResponse<Object> returnStations(String insuranceType,String timeRange){
        PolicyQueryRequest req = new PolicyQueryRequest();
        List<StationDistanceVO> sd= stationMapper.getAllStations();
        String stationNums = sd.stream()
                .map(StationDistanceVO::getStationNum)
                .collect(Collectors.joining(","));
        req.setStationId(stationNums);
        req.setTimeRange(timeRange);
        InsuranceType insType=InsuranceType.valueOf(insuranceType);
        req.setInsuranceType(insType);


        HashMap<String, String> params = getStringStringHashMap(req);
        String TianQingResult = CMADAASService.read("http://10.159.90.120/music-ws/api?", params);
        System.out.println(TianQingResult);
        if (TianQingResult == null || TianQingResult.isEmpty()) {
            System.out.println("No data received from API.");
            return ApiResponse.fail("No data received from TianQing.");
        }

        JSONObject obj = JSON.parseObject(TianQingResult);
        JSONArray dsArray = obj.getJSONArray("DS");
        if (dsArray == null || dsArray.isEmpty()) {
            return ApiResponse.fail("天擎查询失败！");
        }

        if (insType.equals(InsuranceType.GOLDEN_POMPANO)||insType.equals(InsuranceType.SHRIMP)||insType.equals(InsuranceType.OYSTER)) {
//筛选站号
            List<String> highWindStations = dsArray.stream()
                    .map(o -> (JSONObject) o)
                    .filter(record -> {
                        double wind = record.getDoubleValue("MAX_WIN_S_Max");
                        int level = getWindLevel(wind);
                        return level > 10;   // 风级大于10级
                    })
                    .map(record -> record.getString("Station_Id_C"))
                    .distinct()
                    .collect(Collectors.toList());

            List<StationDistanceVO> resultList = sd.stream()
                    .filter(vo -> highWindStations.contains(vo.getStationNum()))
                    .collect(Collectors.toList());
            return ApiResponse.success(resultList);

        } else if(insType.equals(InsuranceType.FC_OYSTER)){
            List<String> highWindStations = dsArray.stream()
                    .map(o -> (JSONObject) o)
                    .filter(record -> {
                        double wind = record.getDoubleValue("MAX_WIN_S_Max");
                        return wind > 17.2;
                    })
                    .map(record -> record.getString("Station_Id_C"))
                    .distinct()
                    .collect(Collectors.toList());
            List<StationDistanceVO> resultList = sd.stream()
                    .filter(vo -> highWindStations.contains(vo.getStationNum()))
                    .collect(Collectors.toList());
            return ApiResponse.success(resultList);
        }

        return ApiResponse.success(null);

    }



    public ApiResponse<Object> makeText(PolicyQueryRequest req) {


        HashMap<String, String> params = getStringStringHashMap(req);
        String TianQingResult = CMADAASService.read("http://10.159.90.120/music-ws/api?", params);
        System.out.println(TianQingResult);
        if (TianQingResult == null || TianQingResult.isEmpty()) {
            System.out.println("No data received from API.");
            return ApiResponse.fail("No data received from TianQing.");
        }

        JSONObject obj = JSON.parseObject(TianQingResult);
        JSONArray dsArray = obj.getJSONArray("DS");
        if (dsArray == null || dsArray.isEmpty()) {
            return ApiResponse.fail("天擎查询失败！");
        }
        long highTempDays=0;
        if (req.getInsuranceType().equals(InsuranceType.MANGROVE)){
            // Map：key = 日期（yyyy-MM-dd），value = 当天最大气温
            Map<String, Double> dateMaxTempMap = new HashMap<>();

            for (int i = 0; i < dsArray.size(); i++) {
                JSONObject item = dsArray.getJSONObject(i);
                String datetime = item.getString("Datetime");
                double temp = item.getDoubleValue("TEM_Max");

                // 提取日期部分 yyyy-MM-dd
                String date = datetime.substring(0, 10);

                // 取最大温度
                dateMaxTempMap.merge(date, temp, Math::max);
            }
// 统计高温（>= 35℃）的天数
             highTempDays = dateMaxTempMap.values().stream()
                    .filter(t -> t >= 35.0)
                    .count();
        }



        double maxWind = dsArray.stream()
                .map(o -> (JSONObject) o)
                .mapToDouble(r -> r.getDoubleValue("MAX_WIN_S_Max"))
                .filter(v -> v >= 0 && v <= 100)
                .max()
                .orElse(0.0);
        double maxTem = dsArray.stream()
                .map(o -> (JSONObject) o)
                .mapToDouble(r -> r.getDoubleValue("MAX_TEM_Max"))
                .max()
                .orElse(0.0);

        double maxRain = dsArray.stream()
                .map(o -> (JSONObject) o)
                .mapToDouble(r -> r.getDoubleValue("MAX_PRE_24h"))
                .max()
                .orElse(0.0);

        InsuranceType insType= req.getInsuranceType();
// 构建保险引擎输入
        InsuranceInput input = new InsuranceInput();
        String description ="";
        if (insType.equals(InsuranceType.GOLDEN_POMPANO)||insType.equals(InsuranceType.SHRIMP)||insType.equals(InsuranceType.OYSTER)) {
            input.setInsuranceType(req.getInsuranceType());
            input.setDisasterType(DisasterType.WIND);
            input.setWindLevel(getWindLevel(maxWind));
            description =  engine.calculateDescription(input);

        }else if(insType.equals(InsuranceType.FC_OYSTER)){
            input.setInsuranceType(req.getInsuranceType());
            input.setDisasterType(DisasterType.TYPHOON);
            input.setWindSpeed(maxWind);
           String descriptionp1 = engine.calculateDescription(input);
//            double rateTyphoon = extractRate(descriptionp1);
//            input.setDisasterType(DisasterType.HEAT_INDEX);
//            input.setTemperatureIndexT(maxTem);
//            String descriptionp2 = engine.calculateDescription(input);
//            double rateHeat = extractRate(descriptionp2);
//            description = rateTyphoon >= rateHeat ? descriptionp1 : descriptionp2;
            description=descriptionp1;

        }else if (insType.equals(InsuranceType.CAMELLIA)){
            input.setInsuranceType(req.getInsuranceType());
            input.setDisasterType(DisasterType.RAIN);
            input.setRainAmount(maxRain);
            description = engine.calculateDescription(input);
        }else if (insType.equals(InsuranceType.MANGROVE)){
            input.setInsuranceType(req.getInsuranceType());
            input.setDisasterType(DisasterType.HEAT_DAYS);
           input.setHotDays((int) highTempDays);
            description = engine.calculateDescription(input);
        }


// 调用引擎生成一句话说明


        String result = String.format("%s。%s的%s",
                timeSTR(req.getTimeRange()),
                req.getStationName(),
                description);


        return ApiResponse.success(result);


    }

    private static HashMap<String, String> getStringStringHashMap(PolicyQueryRequest req) {
        String elements="Station_Id_C";



        HashMap<String, String> params = new HashMap<>();
        params.put("serviceNodeId", "NMIC_MUSIC_CMADAAS");
        params.put("userId", "BENN_QXT_YTHPT");
        params.put("pwd", "GXythpt@kfzx*6!8");
        params.put("dataFormat","json");
        params.put("timeRange", req.getTimeRange());
        params.put("staIds", req.getStationId());
        if (req.getInsuranceType().equals(InsuranceType.MANGROVE)) {
            params.put("interfaceId", "getSurfEleByTimeRangeAndStaID");
            params.put("dataCode", "SURF_CHN_MUL_DAY");
            params.put("elements", "Station_Id_C,Datetime,TEM_Max");
        }else {
            params.put("interfaceId", "statSurfEleByStaID");
            params.put("dataCode", "SURF_CHN_MUL_HOR");
            params.put("statEles","MAX_TEM_Max,MAX_WIN_S_Max,MAX_PRE_24h");
            params.put("elements", elements);
        }

        return params;
    }

    public int getWindLevel(double speed) {
        if (speed < 0||speed>100) return -1; // 非法风速
        if (speed <= 0.2) return 0;
        else if (speed <= 1.5) return 1;
        else if (speed <= 3.3) return 2;
        else if (speed <= 5.4) return 3;
        else if (speed <= 7.9) return 4;
        else if (speed <= 10.7) return 5;
        else if (speed <= 13.8) return 6;
        else if (speed <= 17.1) return 7;
        else if (speed <= 20.7) return 8;
        else if (speed <= 24.4) return 9;
        else if (speed <= 28.4) return 10;
        else if (speed <= 32.6) return 11;
        else return 12;
    }


    public String timeSTR(String time) {
        String period = time;

        period = period.replace("[", "").replace("]", "");

        String[] arr = period.split(",");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime start = LocalDateTime.parse(arr[0], formatter);
        LocalDateTime end = LocalDateTime.parse(arr[1], formatter);
        return String.format("%d年%d月%d日~%d月%d日",
                start.getYear(), start.getMonthValue(), start.getDayOfMonth(),
                end.getMonthValue(), end.getDayOfMonth()
        );
    }

    private double extractRate(String description) {
        try {
            Pattern p = Pattern.compile("(\\d+\\.?\\d*)%");
            Matcher m = p.matcher(description);
            if (m.find()) {
                double percent = Double.parseDouble(m.group(1));
                return percent / 100.0;
            }
        } catch (Exception ignored) {}
        return 0.0;
    }



}
