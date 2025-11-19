package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.CamelliaDroughtRule;
import com.tao.insurance.entity.CamelliaRainRule;
import com.tao.insurance.mapper.CamelliaDroughtRuleMapper;
import com.tao.insurance.mapper.CamelliaRainRuleMapper;
import com.tao.insurance.service.CamelliaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CamelliaServiceImpl implements CamelliaService {

    private final CamelliaRainRuleMapper rainMapper;
    private final CamelliaDroughtRuleMapper droughtMapper;

    @Override
    public Double getRainRate(Double rainAmount) {
        CamelliaRainRule rule = rainMapper.selectOne(
                new LambdaQueryWrapper<CamelliaRainRule>()
                        .le(CamelliaRainRule::getRainMin, rainAmount)
                        .and(w -> w.ge(CamelliaRainRule::getRainMax, rainAmount)
                                .or().isNull(CamelliaRainRule::getRainMax))
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }

    @Override
    public Double getDroughtRate(Double rainAmount) {
        CamelliaDroughtRule rule = droughtMapper.selectOne(
                new LambdaQueryWrapper<CamelliaDroughtRule>()
                        .le(CamelliaDroughtRule::getRainMin, rainAmount)
                        .and(w -> w.ge(CamelliaDroughtRule::getRainMax, rainAmount)
                                .or().isNull(CamelliaDroughtRule::getRainMax))
        );
        return rule == null ? 0D : rule.getCompensateRate();
    }
}
