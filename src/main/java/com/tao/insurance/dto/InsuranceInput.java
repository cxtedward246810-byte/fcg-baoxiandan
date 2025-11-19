package com.tao.insurance.dto;

import com.tao.insurance.common.DisasterType;
import com.tao.insurance.common.InsuranceType;
import lombok.Data;

/**
 * 统一的理赔标准查询入参
 * 根据不同险种 + 灾害类型，使用不同字段
 */
@Data
public class InsuranceInput {

    private String timeRange;

    /** 保险品种类型（必填） */
    private InsuranceType insuranceType;

    /** 灾害类型（必填） */
    private DisasterType disasterType;

    // ===== 通用指标，根据不同险种灾害选择性使用 =====

    /** 风级（如 9~17 级） */
    private Integer windLevel;

    /** 风速（m/s）用于热带气旋等 */
    private Double windSpeed;

    /** 高温指数 T （防城港牡蛎高温灾害） */
    private Double temperatureIndexT;

    /** 赤潮面积 A（平方千米） */
    private Double redTideArea;

    /** 降雨量（mm） */
    private Double rainAmount;

    /** 高温日数 H（红树林高温事故） */
    private Integer hotDays;

    /** 中草药品种名称（肉桂/八角/金花茶…） */
    private String herbType;

    /** 中草药规则指标值（风速/降雨/温度等统一用 metric） */
    private Double herbMetric;

    /** 中草药规则类型（风灾/强降雨/低温 等） */
    private String herbRuleType;
}
