package com.tao.insurance.service;

public interface FcOysterRedTideService {

    /**
     * 根据赤潮类型 + 面积查询赔偿比例
     */
    Double getRate(String tideType, Double area);
}
