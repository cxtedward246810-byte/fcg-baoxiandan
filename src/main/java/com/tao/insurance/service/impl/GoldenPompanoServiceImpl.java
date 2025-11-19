package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.GoldenPompanoWindRule;
import com.tao.insurance.mapper.GoldenPompanoWindRuleMapper;
import com.tao.insurance.service.GoldenPompanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoldenPompanoServiceImpl implements GoldenPompanoService {

    private final GoldenPompanoWindRuleMapper mapper;

    @Override
    public Double getCompensateRateByWind(Integer windLevel) {
        GoldenPompanoWindRule rule = mapper.selectOne(
                new LambdaQueryWrapper<GoldenPompanoWindRule>()
                        .eq(GoldenPompanoWindRule::getWindLevel, windLevel)
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
