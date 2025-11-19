package com.tao.insurance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.entity.BaoDanResult;
import com.tao.insurance.service.BaoDanResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/baodan")
public class BaoDanResultController {

    @Autowired
    private BaoDanResultService baoDanResultService;

    @GetMapping("/list")
    public ApiResponse<IPage<BaoDanResult>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sTime,
            @RequestParam(required = false) String eTime,
            @RequestParam(required = false) String insuranceType,
            @RequestParam(required = false) String insuranceName) {

        Page<BaoDanResult> p = new Page<>(page, size);

        LambdaQueryWrapper<BaoDanResult> query = new LambdaQueryWrapper<>();

        // 1. 保险类型（精确匹配）
        query.eq(StringUtils.hasText(insuranceType),
                BaoDanResult::getInsuranceType, insuranceType);

        // 2. 保险名称（模糊查询）
        query.like(StringUtils.hasText(insuranceName),
                BaoDanResult::getInsuranceName, insuranceName);

        // 3. 开始时间（>=）
        query.ge(StringUtils.hasText(sTime),
                BaoDanResult::getCreateTime, sTime);

        // 4. 结束时间（<=）
        query.le(StringUtils.hasText(eTime),
                BaoDanResult::getCreateTime, eTime);

        // 排序（最新在前）
        query.orderByDesc(BaoDanResult::getCreateTime);

        IPage<BaoDanResult> result = baoDanResultService.page(p, query);

        return ApiResponse.success(result);
    }
    /**
     * 删除（根据 ID）
     */
    @GetMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        return baoDanResultService.removeById(id);
    }
}
