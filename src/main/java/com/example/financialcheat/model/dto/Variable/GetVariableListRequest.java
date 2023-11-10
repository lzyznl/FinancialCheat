package com.example.financialcheat.model.dto.Variable;

import lombok.Data;

@Data
public class GetVariableListRequest {
    private long fileId;
    private String fileType;
}
