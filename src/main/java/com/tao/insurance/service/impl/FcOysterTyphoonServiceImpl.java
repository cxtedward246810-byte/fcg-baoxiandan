package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.FcOysterTyphoonRule;
import com.tao.insurance.mapper.FcOysterTyphoonRuleMapper;
import com.tao.insurance.service.FcOysterTyphoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcOysterTyphoonServiceImpl implements FcOysterTyphoonService {

    private final FcOysterTyphoonRuleMapper mapper;

    @Override
    public Double getRateByWindSpeed(Double windSpeed) {

        FcOysterTyphoonRule rule = mapper.selectOne(
                new LambdaQueryWrapper<FcOysterTyphoonRule>()
                        .le(FcOysterTyphoonRule::getWindSpeedMin, windSpeed)
                        .and(w -> w.ge(FcOysterTyphoonRule::getWindSpeedMax, windSpeed)
                                .or().isNull(FcOysterTyphoonRule::getWindSpeedMax))
        );

        return rule == null ? 0D : rule.getCompensateRate();
    }
}
