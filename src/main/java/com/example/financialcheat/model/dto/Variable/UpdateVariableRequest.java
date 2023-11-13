package com.example.financialcheat.model.dto.Variable;


import lombok.Data;

@Data
public class UpdateVariableRequest {
    private Long id;
    private String variableName;
    private String variableType;
    private String description;
    private String value;
}
