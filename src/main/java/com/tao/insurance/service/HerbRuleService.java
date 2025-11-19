package com.tao.insurance.service;

public interface HerbRuleService {

    Double getRate(String herbType, String ruleType, Double metric);
}
