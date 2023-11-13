package com.example.financialcheat.model.dto.rule;

import lombok.Data;

@Data
public class UpdateRuleRequest {

    private Integer ruleId;

    private String rule;

    private String ruleName;
}
