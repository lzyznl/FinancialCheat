package com.example.financialcheat.model.dto.rule;


import lombok.Data;

@Data
public class GetRuleAddRequest {

    private String rule;

    private Long fileId;

    private Long projectId;
}
