package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 中草药气象规则（统一规则表）
 * 表：ins_herb_rule
 */
@Data
@TableName("ins_herb_rule")
public class HerbRule {

    @TableId
    private Long id;

    /** 中草药品种：肉桂、八角、金花茶等 */
    private String herbType;

    /** 规则类型：风灾/强降雨/低温 等 */
    private String ruleType;

    /** 指标起止值：风速/雨量/温度等 */
    private Double metricMin;
    private Double metricMax;

    /** 赔偿比例 */
    private Double compensateRate;

    private String remark;
}
