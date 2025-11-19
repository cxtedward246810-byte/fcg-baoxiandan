package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.FarmSite;
import com.tao.insurance.mapper.FarmSiteMapper;
import com.tao.insurance.service.FarmSiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场点服务实现
 */
@Service
@RequiredArgsConstructor
public class FarmSiteServiceImpl implements FarmSiteService {

    private final FarmSiteMapper mapper;

    @Override
    public List<FarmSite> listByType(String insuranceType) {
        return mapper.selectList(
                new LambdaQueryWrapper<FarmSite>()
                        .eq(FarmSite::getInsuranceType, insuranceType)
        );
    }

    @Override
    public List<FarmSite> listAll() {
        return mapper.selectList(null);
    }

    @Override
    public FarmSite getById(Long id) {
        return mapper.selectById(id);
    }
}
