package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 防城港牡蛎 高温指数T → 赔偿比例或公式
 * 表：ins_fc_oyster_heat_rule
 */
@Data
@TableName("ins_fc_oyster_heat_rule")
public class FcOysterHeatRule {

    @TableId
    private Long id;

    private Double tempMin;
    private Double tempMax;       // 可为 null 表示无上限

    /** 若是直接给固定赔偿比例，填这里 */
    private Double compensateRateFixed;

    /** 若是“分段公式”则存公式，如 "(T-1)*0.7%+0.6%" */
    private String formula;

    private String remark;
}
