package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 金花茶 日降雨量 → 赔偿系数
 * 表：ins_camellia_rain_rule
 */
@Data
@TableName("ins_camellia_rain_rule")
public class CamelliaRainRule {

    @TableId
    private Long id;

    private Double rainMin;
    private Double rainMax;

    private Double compensateRate;

    private String remark;
}
