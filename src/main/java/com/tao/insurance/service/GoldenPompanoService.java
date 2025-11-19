package com.tao.insurance.service;

/**
 * 金鲳鱼理赔规则服务
 */
public interface GoldenPompanoService {

    /**
     * 根据风级查询赔偿比例
     */
    Double getCompensateRateByWind(Integer windLevel);
}
