package com.example.financialcheat.model.vo.VariableVo;

import lombok.Data;

@Data
public class variableListVo {
    private long id;
    private String variableName;
    private String variableType;
    private String description;
    private String value;
}
