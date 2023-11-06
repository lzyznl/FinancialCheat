package com.example.financialcheat.model.dto.rule;

import lombok.Data;

@Data
public class AddRuleHistoryRequest {
    private long projectId;
    private long ruleId;
    private String userName;
    private String event;
}
