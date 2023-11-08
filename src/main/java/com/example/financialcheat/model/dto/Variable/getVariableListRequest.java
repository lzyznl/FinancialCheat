package com.example.financialcheat.model.dto.Variable;

import lombok.Data;

@Data
public class getVariableListRequest {
    private long fileId;
    private String fileType;
}
