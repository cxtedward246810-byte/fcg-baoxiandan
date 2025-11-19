package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.MangroveHeatDaysRule;
import com.tao.insurance.entity.MangroveWindRule;
import com.tao.insurance.mapper.MangroveHeatDaysRuleMapper;
import com.tao.insurance.mapper.MangroveWindRuleMapper;
import com.tao.insurance.service.MangroveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangroveServiceImpl implements MangroveService {

    private final MangroveHeatDaysRuleMapper heatMapper;
    private final MangroveWindRuleMapper windMapper;

    @Override
    public String getHeatFormula(Integer hotDays) {
        MangroveHeatDaysRule rule = heatMapper.selectOne(
                new LambdaQueryWrapper<MangroveHeatDaysRule>()
                        .le(MangroveHeatDaysRule::getHMin, hotDays)
                        .and(w -> w.ge(MangroveHeatDaysRule::getHMax, hotDays)
                                .or().isNull(MangroveHeatDaysRule::getHMax))
        );
        return rule == null ? null : rule.getCompensateRateFormula();
    }

    @Override
    public Double getWindRate(Double windSpeed) {
        MangroveWindRule rule = windMapper.selectOne(
                new LambdaQueryWrapper<MangroveWindRule>()
                        .le(MangroveWindRule::getWindSpeedMin, windSpeed)
                        .and(w -> w.ge(MangroveWindRule::getWindSpeedMax, windSpeed)
                                .or().isNull(MangroveWindRule::getWindSpeedMax))
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
