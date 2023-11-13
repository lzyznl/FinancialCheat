package com.example.financialcheat.model.dto.rule;

import lombok.Data;

@Data
public class RuleStatusChangeRequest {
    private long projectId;
    private long ruleId;
    private int status;
}
