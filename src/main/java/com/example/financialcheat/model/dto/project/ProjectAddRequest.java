package com.example.financialcheat.model.dto.project;

import lombok.Data;

@Data
public class ProjectAddRequest {
    private static final long serialVersionUID = 3191241716373120793L;

    private String projectName;

    /**
     * 新建项目的用户在项目中的职位，0 - 队员；1 - 队长
     */
    private int position;
}
