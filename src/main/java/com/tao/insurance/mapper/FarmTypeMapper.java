package com.tao.insurance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tao.insurance.entity.FarmType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 养殖类型查询 Mapper
 */
@Mapper
public interface FarmTypeMapper extends BaseMapper<FarmType> {
}