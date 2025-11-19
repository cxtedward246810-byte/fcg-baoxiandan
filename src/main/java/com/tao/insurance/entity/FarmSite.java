package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 场点表
 * 对应 farm_site
 */
@Data
@TableName("farm_site")
public class FarmSite {

    @TableId
    private Long id;

    private String siteName;

    private Double lng;   // 经度
    private Double lat;   // 纬度

    private String insuranceType;  // 对应 farm_type.type_code
    private String remark;
}