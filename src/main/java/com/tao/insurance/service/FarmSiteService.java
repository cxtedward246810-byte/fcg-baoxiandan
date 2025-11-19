package com.tao.insurance.service;

import com.tao.insurance.entity.FarmSite;

import java.util.List;

public interface FarmSiteService {

    /**
     * 查询某养殖类型下的全部场点
     */
    List<FarmSite> listByType(String insuranceType);

    /**
     * 查询全部场点
     */
    List<FarmSite> listAll();

    /**
     * 根据ID查询
     */
    FarmSite getById(Long id);
}
