package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 红树林 高温日数 H → 赔偿公式
 * 表：ins_mangrove_heat_days_rule
 */
@Data
@TableName("ins_mangrove_heat_days_rule")
public class MangroveHeatDaysRule {

    @TableId
    private Long id;

    private Integer hMin;
    private Integer hMax; // 可为 null

    /** 赔偿公式，例如 "1.5%*n"、"50%*n" */
    private String compensateRateFormula;

    private String remark;
}
