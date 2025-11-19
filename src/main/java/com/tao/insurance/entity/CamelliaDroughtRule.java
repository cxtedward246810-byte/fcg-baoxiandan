package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 金花茶 干旱等级（雨量区间） → 赔偿系数
 * 表：ins_camellia_drought_rule
 */
@Data
@TableName("ins_camellia_drought_rule")
public class CamelliaDroughtRule {

    @TableId
    private Long id;

    /** 干旱等级 1~5 */
    private Integer droughtLevel;

    private Double rainMin;
    private Double rainMax;

    private Double compensateRate;

    private String remark;
}
