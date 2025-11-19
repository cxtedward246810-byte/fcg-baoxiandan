package com.tao.insurance.common;

/**
 * 保险品种类型枚举
 * 对应：金鲳鱼、对虾、牡蛎、防城港牡蛎、金花茶、红树林、中草药
 */
public enum InsuranceType {

    GOLDEN_POMPANO,   // 金鲳鱼养殖风力指数保险
    SHRIMP,           // 对虾养殖风力指数保险
    OYSTER,           // 普通牡蛎风力指数保险
    FC_OYSTER,        // 防城港市地方牡蛎养殖气象指数保险
    CAMELLIA,         // 金花茶综合气象指数保险
    MANGROVE,         // 红树林特色气象指数保险
    HERB              // 防城港市中草药气象指数保险
}
