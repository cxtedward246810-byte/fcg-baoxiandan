package com.tao.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ins_BaoDanResult")
public class BaoDanResult {
    @TableId
    private long id;
    private String beginTime;
    private String endTime;
    private String insuranceType;
    private String pdfUrl;
    private String createTime;
    private String insuranceName;
}
