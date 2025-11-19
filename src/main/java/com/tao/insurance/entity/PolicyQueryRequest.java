package com.tao.insurance.entity;

import com.tao.insurance.common.InsuranceType;
import lombok.Data;

@Data
public class PolicyQueryRequest {
    private String stationId;//自动站
    private String stationName;//金鲳鱼等
    private String timeRange;
    private InsuranceType insuranceType;


}
