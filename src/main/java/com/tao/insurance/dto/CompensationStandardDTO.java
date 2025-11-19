package com.tao.insurance.dto;

import com.tao.insurance.common.DisasterType;
import com.tao.insurance.common.InsuranceType;
import lombok.Data;

/**
 * 统一返回的理赔标准信息
 */
@Data
public class CompensationStandardDTO {

    private InsuranceType insuranceType;

    private DisasterType disasterType;

    /** 赔偿比例（0~1），适用大部分险种 */
    private Double compensateRate;

    /** 固定每亩赔偿金额（如牡蛎按风级直接给多少钱/亩） */
    private Double compensateAmountPerUnit;

    /** 公式字符串（如 T*0.6% 或 1.5%*n），有些险种需要 */
    private String formula;

    /** 说明信息 */
    private String remark;
}
