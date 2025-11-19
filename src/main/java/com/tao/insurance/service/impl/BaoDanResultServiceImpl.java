package com.tao.insurance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tao.insurance.entity.BaoDanResult;
import com.tao.insurance.mapper.BaoDanResultMapper;
import com.tao.insurance.service.BaoDanResultService;
import org.springframework.stereotype.Service;

@Service
public class BaoDanResultServiceImpl
        extends ServiceImpl<BaoDanResultMapper, BaoDanResult>
        implements BaoDanResultService {
}
