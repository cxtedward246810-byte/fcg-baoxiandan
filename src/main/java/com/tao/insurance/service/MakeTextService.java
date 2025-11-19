package com.tao.insurance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.common.DisasterType;
import com.tao.insurance.common.InsuranceType;
import com.tao.insurance.dto.InsuranceInput;
import com.tao.insurance.engine.InsuranceCalculationEngine;
import com.tao.insurance.entity.BaoDanResult;
import com.tao.insurance.entity.PolicyQueryRequest;
import com.tao.service.CMADAASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MakeTextService {

    @Autowired
    private  InsuranceCalculationEngine engine;

    public ApiResponse<Object> makeText(PolicyQueryRequest req) {

        HashMap<String, String> params = getStringStringHashMap(req);
        String TianQingResult = CMADAASService.read("http://10.159.90.120/music-ws/api?", params);
        System.out.println(TianQingResult);
        if (TianQingResult == null || TianQingResult.isEmpty()) {
            System.out.println("No data received from API.");
            return ApiResponse.success("No data received from API.");
        }

        JSONObject obj = JSON.parseObject(TianQingResult);
        JSONArray dsArray = obj.getJSONArray("DS");

// 直接求所有记录中的最大风速（不区分站点）
        double maxWind = dsArray.stream()
                .map(o -> (JSONObject) o)
                .mapToDouble(r -> r.getDoubleValue("MAX_WIN_S_Max"))
                .filter(v -> v >= 0 && v <= 100)
                .max()
                .orElse(0.0);

// 构建保险引擎输入
        InsuranceInput input = new InsuranceInput();
        input.setInsuranceType(req.getInsuranceType());
        input.setDisasterType(DisasterType.WIND);
        input.setWindLevel(getWindLevel(maxWind));

// 调用引擎生成一句话说明
        String description = engine.calculateDescription(input);

        String result = String.format("%s。%s最大风速：%.1f m/s。%s",
                timeSTR(req.getTimeRange()),
                req.getStationName(),
                maxWind,
                description);


        return ApiResponse.success(result);


    }

    private static HashMap<String, String> getStringStringHashMap(PolicyQueryRequest req) {
        String elements="Station_Id_C";

        HashMap<String, String> params = new HashMap<>();
        params.put("serviceNodeId", "NMIC_MUSIC_CMADAAS");
        params.put("userId", "BENN_QXT_YTHPT");
        params.put("pwd", "GXythpt@kfzx*6!8");
        params.put("interfaceId", "statSurfEleByStaID");
        params.put("dataCode", "SURF_CHN_MUL_HOR");
        params.put("timeRange", req.getTimeRange());
        params.put("staIds", req.getStationId());
        params.put("statEles", "MAX_WIN_S_Max");
        params.put("elements", elements);
        params.put("dataFormat","json");
        return params;
    }

    public int getWindLevel(double speed) {
        if (speed < 0) return -1; // 非法风速

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
}
