package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.HerbRule;
import com.tao.insurance.mapper.HerbRuleMapper;
import com.tao.insurance.service.HerbRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HerbRuleServiceImpl implements HerbRuleService {

    private final HerbRuleMapper mapper;

    @Override
    public Double getRate(String herbType, String ruleType, Double metric) {
        HerbRule rule = mapper.selectOne(
                new LambdaQueryWrapper<HerbRule>()
                        .eq(HerbRule::getHerbType, herbType)
                        .eq(HerbRule::getRuleType, ruleType)
                        .le(HerbRule::getMetricMin, metric)
                        .and(w -> w.ge(HerbRule::getMetricMax, metric)
                                .or().isNull(HerbRule::getMetricMax))
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
