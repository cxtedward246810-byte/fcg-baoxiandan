package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 金鲳鱼 风力赔偿规则
 * 表：ins_golden_pompano_wind_rule
 */
@Data
@TableName("ins_golden_pompano_wind_rule")
public class GoldenPompanoWindRule {

    @TableId
    private Long id;

    /** 风级 */
    private Integer windLevel;

    /** 赔偿比例 0~1 */
    private Double compensateRate;

    private String remark;
}
