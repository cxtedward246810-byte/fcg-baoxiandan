package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 养殖类型表
 * 对应表：farm_type
 */
@Data
@TableName("farm_type")
public class FarmType {

    @TableId
    private Long id;

    private String typeCode;     // 对应 InsuranceType 枚举值
    private String typeName;     // 中文名
    private String description;  // 说明
}