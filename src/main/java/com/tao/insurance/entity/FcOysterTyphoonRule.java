package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 防城港牡蛎 热带气旋 风速 → 赔偿比例
 * 表：ins_fc_oyster_typhoon_rule
 */
@Data
@TableName("ins_fc_oyster_typhoon_rule")
public class FcOysterTyphoonRule {

    @TableId
    private Long id;

    private Integer windLevel;

    private Double windSpeedMin;
    private Double windSpeedMax; // 可为 null 代表无上限

    private Double compensateRate;

    private String remark;
}
