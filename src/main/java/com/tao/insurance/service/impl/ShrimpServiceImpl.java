package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.ShrimpWindRule;
import com.tao.insurance.mapper.ShrimpWindRuleMapper;
import com.tao.insurance.service.ShrimpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShrimpServiceImpl implements ShrimpService {

    private final ShrimpWindRuleMapper mapper;

    @Override
    public Double getCompensateRate(Integer windLevel) {
        ShrimpWindRule rule = mapper.selectOne(
                new LambdaQueryWrapper<ShrimpWindRule>()
                        .eq(ShrimpWindRule::getWindLevel, windLevel)
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
