package com.tao.insurance.service;


import com.tao.insurance.entity.FarmType;
import java.util.List;

/**
 * 养殖类型服务接口
 */
public interface FarmTypeService {

    /**
     * 查询所有养殖类型
     */
    List<FarmType> listAll();

    /**
     * 根据 typeCode 查询一条
     */
    FarmType getByCode(String typeCode);
}
