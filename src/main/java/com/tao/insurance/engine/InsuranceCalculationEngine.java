package com.tao.insurance.engine;

import com.tao.insurance.dto.CompensationStandardDTO;
import com.tao.insurance.dto.InsuranceInput;

/**
 * 统一理赔标准计算引擎
 * 只负责查出赔偿比例/金额/公式，不算最终赔偿金额
 */
public interface InsuranceCalculationEngine {

    /**
     * 返回结构化 DTO（前端不直接显示）
     */
    CompensationStandardDTO calculate(InsuranceInput input);

    /**
     * 返回自然语言文本（保单说明）
     */
    String calculateDescription(InsuranceInput input);
}
