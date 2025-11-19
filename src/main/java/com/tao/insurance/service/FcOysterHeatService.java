package com.tao.insurance.service;

import com.tao.insurance.entity.FcOysterHeatRule;

public interface FcOysterHeatService {

    /**
     * 根据高温指数T查询规则
     */
    FcOysterHeatRule getRuleByT(Double T);
}
