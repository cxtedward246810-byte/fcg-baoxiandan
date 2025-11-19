package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 对虾 风力赔偿规则
 * 表：ins_shrimp_wind_rule
 */
@Data
@TableName("ins_shrimp_wind_rule")
public class ShrimpWindRule {

    @TableId
    private Long id;

    private Integer windLevel;

    private Double compensateRate;

    private String remark;
}
