package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tao.insurance.entity.FarmType;
import com.tao.insurance.mapper.FarmTypeMapper;
import com.tao.insurance.service.FarmTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 养殖类型服务实现
 */
@Service
@RequiredArgsConstructor
public class FarmTypeServiceImpl implements FarmTypeService {

    private final FarmTypeMapper mapper;

    @Override
    public List<FarmType> listAll() {
        return mapper.selectList(null);
    }

    @Override
    public FarmType getByCode(String typeCode) {
        return mapper.selectOne(
                new LambdaQueryWrapper<FarmType>()
                        .eq(FarmType::getTypeCode, typeCode)
        );
    }
}
