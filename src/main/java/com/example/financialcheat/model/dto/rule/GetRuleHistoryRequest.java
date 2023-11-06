package com.example.financialcheat.model.dto.rule;


import lombok.Data;

@Data
public class GetRuleHistoryRequest {
    private long projectId;
    private long ruleId;
}
