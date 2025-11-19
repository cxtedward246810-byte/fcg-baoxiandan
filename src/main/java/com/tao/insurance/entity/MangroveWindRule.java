package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 红树林 风速 W → 赔偿比例
 * 表：ins_mangrove_wind_rule
 */
@Data
@TableName("ins_mangrove_wind_rule")
public class MangroveWindRule {

    @TableId
    private Long id;

    private Double windSpeedMin;
    private Double windSpeedMax;

    private Double compensateRate;

    private String remark;
}
