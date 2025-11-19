package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.FcOysterRedTideRule;
import com.tao.insurance.mapper.FcOysterRedTideRuleMapper;
import com.tao.insurance.service.FcOysterRedTideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcOysterRedTideServiceImpl implements FcOysterRedTideService {

    private final FcOysterRedTideRuleMapper mapper;

    @Override
    public Double getRate(String tideType, Double area) {
        FcOysterRedTideRule rule = mapper.selectOne(
                new LambdaQueryWrapper<FcOysterRedTideRule>()
                        .eq(FcOysterRedTideRule::getTideType, tideType)
                        .le(FcOysterRedTideRule::getAreaMin, area)
                        .and(w -> w.ge(FcOysterRedTideRule::getAreaMax, area)
                                .or().isNull(FcOysterRedTideRule::getAreaMax))
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
