package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.OysterWindRule;
import com.tao.insurance.mapper.OysterWindRuleMapper;
import com.tao.insurance.service.OysterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OysterServiceImpl implements OysterService {

    private final OysterWindRuleMapper mapper;

    @Override
    public Double getCompensateAmount(Integer windLevel) {
        OysterWindRule rule = mapper.selectOne(
                new LambdaQueryWrapper<OysterWindRule>()
                        .eq(OysterWindRule::getWindLevel, windLevel)
        );
        return rule == null ? 0D : rule.getCompensateAmount();
    }
}
