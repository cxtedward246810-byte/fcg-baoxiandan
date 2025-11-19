package com.tao.insurance.entity;

import lombok.Data;

@Data
public class PdfRequest {
    private String description;
    private String timeRange;
    private String insuranceType;
    private String insuranceName;
}