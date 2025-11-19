package com.tao.insurance.engine.impl;

import com.tao.insurance.common.DisasterType;
import com.tao.insurance.common.InsuranceType;
import com.tao.insurance.dto.CompensationStandardDTO;
import com.tao.insurance.dto.InsuranceInput;
import com.tao.insurance.engine.InsuranceCalculationEngine;
import com.tao.insurance.entity.FcOysterHeatRule;
import com.tao.insurance.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsuranceCalculationEngineImpl implements InsuranceCalculationEngine {

    private final GoldenPompanoService goldenPompanoService;
    private final ShrimpService shrimpService;
    private final OysterService oysterService;
    private final FcOysterTyphoonService fcOysterTyphoonService;
    private final FcOysterHeatService fcOysterHeatService;
    private final FcOysterRedTideService fcOysterRedTideService;
    private final CamelliaService camelliaService;
    private final MangroveService mangroveService;
    private final HerbRuleService herbRuleService;

    /**
     * 返回结构化 DTO
     */
    @Override
    public CompensationStandardDTO calculate(InsuranceInput input) {

        CompensationStandardDTO dto = new CompensationStandardDTO();
        dto.setInsuranceType(input.getInsuranceType());
        dto.setDisasterType(input.getDisasterType());

        switch (input.getInsuranceType()) {

            case GOLDEN_POMPANO:
                dto.setCompensateRate(
                        goldenPompanoService.getCompensateRateByWind(input.getWindLevel())
                );
                dto.setRemark("金鲳鱼风力指数保险：按风级匹配赔偿比例");
                return dto;

            case SHRIMP:
                dto.setCompensateRate(
                        shrimpService.getCompensateRate(input.getWindLevel())
                );
                dto.setRemark("对虾风力指数保险：按风级匹配赔偿比例");
                return dto;

            case OYSTER:
                dto.setCompensateAmountPerUnit(
                        oysterService.getCompensateAmount(input.getWindLevel())
                );
                dto.setRemark("普通牡蛎：按风级固定赔偿金额");
                return dto;

            case FC_OYSTER:
                return handleFcOyster(input, dto);

            case CAMELLIA:
                return handleCamellia(input, dto);

            case MANGROVE:
                return handleMangrove(input, dto);

            case HERB:
                dto.setCompensateRate(
                        herbRuleService.getRate(input.getHerbType(),
                                input.getHerbRuleType(),
                                input.getHerbMetric())
                );
                dto.setRemark("中草药：按品种 + 灾害类型 + 指标匹配赔偿比例");
                return dto;

            default:
                dto.setRemark("未知险种");
                return dto;
        }
    }

    /**
     * 返回自然语言描述
     */
    @Override
    public String calculateDescription(InsuranceInput input) {
        CompensationStandardDTO dto = calculate(input);

        // 动态文本生成
        switch (input.getInsuranceType()) {

            case GOLDEN_POMPANO:
                return String.format(
                        "金鲳鱼风力指数保险：风级 %d 级，对应赔偿比例 %.2f%%。",
                        input.getWindLevel(),
                        dto.getCompensateRate() * 100
                );

            case SHRIMP:
                return String.format(
                        "对虾风力指数保险：风级 %d 级，对应赔偿比例 %.2f%%。",
                        input.getWindLevel(),
                        dto.getCompensateRate() * 100
                );

            case OYSTER:
                return String.format(
                        "牡蛎风力指数保险：风级 %d 级，每亩赔偿 %.2f 元。",
                        input.getWindLevel(),
                        dto.getCompensateAmountPerUnit()
                );

            case FC_OYSTER:
                return generateFcOysterDescription(input, dto);

            case CAMELLIA:
                return generateCamelliaDescription(input, dto);

            case MANGROVE:
                return generateMangroveDescription(input, dto);

            case HERB:
                return String.format(
                        "中草药（%s）%s灾害：指标 %.2f，对应赔偿比例 %.2f%%。",
                        input.getHerbType(),
                        input.getHerbRuleType(),
                        input.getHerbMetric(),
                        dto.getCompensateRate() * 100
                );

            default:
                return "未知保险类型。";
        }
    }

    // -------------------- 多险种子处理 --------------------

    private CompensationStandardDTO handleFcOyster(InsuranceInput input, CompensationStandardDTO dto) {

        switch (input.getDisasterType()) {
            case TYPHOON:
                dto.setCompensateRate(
                        fcOysterTyphoonService.getRateByWindSpeed(input.getWindSpeed())
                );
                dto.setRemark("防城港牡蛎-热带气旋");
                return dto;

            case HEAT_INDEX:
                FcOysterHeatRule rule = fcOysterHeatService.getRuleByT(input.getTemperatureIndexT());
                if (rule != null) {
                    dto.setCompensateRate(rule.getCompensateRateFixed());
                    dto.setFormula(rule.getFormula());
                }
                dto.setRemark("防城港牡蛎-高温指数T");
                return dto;

            case RED_TIDE:
                dto.setCompensateRate(
                        fcOysterRedTideService.getRate(input.getHerbRuleType(), input.getRedTideArea())
                );
                dto.setRemark("防城港牡蛎-赤潮");
                return dto;

            default:
                dto.setRemark("防城港牡蛎不支持该灾害类型");
                return dto;
        }
    }

    private CompensationStandardDTO handleCamellia(InsuranceInput input, CompensationStandardDTO dto) {
        if (input.getDisasterType() == DisasterType.RAIN) {
            dto.setCompensateRate(camelliaService.getRainRate(input.getRainAmount()));
            dto.setRemark("金花茶-日降雨量");
        } else if (input.getDisasterType() == DisasterType.DROUGHT) {
            dto.setCompensateRate(camelliaService.getDroughtRate(input.getRainAmount()));
            dto.setRemark("金花茶-干旱等级");
        }
        return dto;
    }

    private CompensationStandardDTO handleMangrove(InsuranceInput input, CompensationStandardDTO dto) {
        if (input.getDisasterType() == DisasterType.HEAT_DAYS) {
            dto.setFormula(mangroveService.getHeatFormula(input.getHotDays()));
            dto.setRemark("红树林-高温日数");
        } else if (input.getDisasterType() == DisasterType.WIND) {
            dto.setCompensateRate(mangroveService.getWindRate(input.getWindSpeed()));
            dto.setRemark("红树林-风灾");
        }
        return dto;
    }


    // -------------------- 文本生成 --------------------

    private String generateFcOysterDescription(InsuranceInput input, CompensationStandardDTO dto) {

        switch (input.getDisasterType()) {
            case TYPHOON:
                return String.format(
                        "防城港牡蛎-热带气旋：风速 %.1f m/s，赔偿比例 %.2f%%。",
                        input.getWindSpeed(),
                        dto.getCompensateRate() * 100
                );
            case HEAT_INDEX:
                if (dto.getFormula() != null) {
                    return String.format(
                            "防城港牡蛎-高温指数T：T = %.2f，适用公式 %s。",
                            input.getTemperatureIndexT(),
                            dto.getFormula()
                    );
                }
                return String.format(
                        "防城港牡蛎-高温指数T：T = %.2f，赔偿比例 %.2f%%。",
                        input.getTemperatureIndexT(),
                        dto.getCompensateRate() * 100
                );
            case RED_TIDE:
                return String.format(
                        "防城港牡蛎-赤潮：类型 %s，面积 %.1f km²，赔偿比例 %.2f%%。",
                        input.getHerbRuleType(),
                        input.getRedTideArea(),
                        dto.getCompensateRate() * 100
                );
            default:
                return "防城港牡蛎：未知灾害类型";
        }
    }

    private String generateCamelliaDescription(InsuranceInput input, CompensationStandardDTO dto) {
        if (input.getDisasterType() == DisasterType.RAIN) {
            return String.format(
                    "金花茶-强降雨：日降雨量 %.1f mm，赔偿比例 %.2f%%。",
                    input.getRainAmount(),
                    dto.getCompensateRate() * 100
            );
        }
        return String.format(
                "金花茶-干旱：降雨量 %.1f mm，对应赔偿比例 %.2f%%。",
                input.getRainAmount(),
                dto.getCompensateRate() * 100
        );
    }

    private String generateMangroveDescription(InsuranceInput input, CompensationStandardDTO dto) {
        if (input.getDisasterType() == DisasterType.HEAT_DAYS) {
            return String.format(
                    "红树林-高温事故：高温天数 %d 天，赔偿公式：%s。",
                    input.getHotDays(),
                    dto.getFormula()
            );
        }
        return String.format(
                "红树林-风灾事故：风速 %.1f m/s，对应赔偿比例 %.2f%%。",
                input.getWindSpeed(),
                dto.getCompensateRate() * 100
        );
    }
}
