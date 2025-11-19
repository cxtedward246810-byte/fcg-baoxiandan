package com.tao.insurance.controller;

import com.tao.insurance.common.ApiResponse;
import com.tao.insurance.entity.FarmType;
import com.tao.insurance.service.FarmTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 养殖类型前端查询接口
 */
@CrossOrigin
@RestController
@RequestMapping("/api/farm/type")
@RequiredArgsConstructor
public class FarmTypeController {

    private final FarmTypeService farmTypeService;

    @GetMapping("/getType")
    public ApiResponse<List<FarmType>> list() {
        return ApiResponse.success(farmTypeService.listAll());
    }
}
