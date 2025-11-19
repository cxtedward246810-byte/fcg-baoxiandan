package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 防城港牡蛎 赤潮面积 → 赔偿比例
 * 表：ins_fc_oyster_redtide_rule
 */
@Data
@TableName("ins_fc_oyster_redtide_rule")
public class FcOysterRedTideRule {

    @TableId
    private Long id;

    /** 赤潮类型：有毒 / 无毒 */
    private String tideType;

    private Double areaMin;
    private Double areaMax;   // 可为 null

    private Double compensateRate;

    private String remark;
}
