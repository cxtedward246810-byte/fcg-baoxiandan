package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 普通牡蛎 风力 → 每亩固定赔偿金额
 * 表：ins_oyster_wind_rule
 */
@Data
@TableName("ins_oyster_wind_rule")
public class OysterWindRule {

    @TableId
    private Long id;

    private Integer windLevel;

    /** 每亩赔偿金额（元/亩） */
    private Double compensateAmount;

    private String remark;
}
