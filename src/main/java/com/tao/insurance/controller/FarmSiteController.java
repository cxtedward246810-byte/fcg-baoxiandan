package com.tao.insurance.controller;

import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.entity.FarmSite;
import com.tao.insurance.service.FarmSiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 场点前端查询接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/farm/site")
@RequiredArgsConstructor
public class FarmSiteController {

    private final FarmSiteService farmSiteService;

    /**
     * 根据养殖类型查询场点
     */
    @GetMapping("/getTypeList")
    public ApiResponse<List<FarmSite>> listByType(@RequestParam(value = "type",required = false) String type) {
        if (type==null||type.isEmpty()){
            return ApiResponse.success(farmSiteService.listAll());
        }
        return ApiResponse.success(farmSiteService.listByType(type));
    }

//    @GetMapping("/all")
//    public ApiResponse<List<FarmSite>> listAll() {
//        return ApiResponse.success(farmSiteService.listAll());
//    }
}
