package com.example.financialcheat.model.dto.rule;

import lombok.Data;

@Data
public class DeleteRuleRequest {
    private long projectId;
    private long ruleId;
}
