package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.FcOysterHeatRule;
import com.tao.insurance.mapper.FcOysterHeatRuleMapper;
import com.tao.insurance.service.FcOysterHeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcOysterHeatServiceImpl implements FcOysterHeatService {

    private final FcOysterHeatRuleMapper mapper;

    @Override
    public FcOysterHeatRule getRuleByT(Double T) {

        return mapper.selectOne(
                new LambdaQueryWrapper<FcOysterHeatRule>()
                        .le(FcOysterHeatRule::getTempMin, T)
                        .and(w -> w.ge(FcOysterHeatRule::getTempMax, T)
                                .or().isNull(FcOysterHeatRule::getTempMax))
        );
    }
}
