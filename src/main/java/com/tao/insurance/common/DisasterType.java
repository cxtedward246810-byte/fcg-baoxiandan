package com.tao.insurance.common;

/**
 * 灾害类型枚举
 * 用于同一险种下区分不同灾害维度
 */
public enum DisasterType {

    WIND,           // 风力等级（风级）
    TYPHOON,        // 热带气旋（按风速）
    HEAT_INDEX,     // 高温指数 T
    RED_TIDE,       // 赤潮面积
    RAIN,           // 降雨量
    DROUGHT,        // 干旱等级/降雨量不足
    HEAT_DAYS       // 高温日数（红树林）
}
