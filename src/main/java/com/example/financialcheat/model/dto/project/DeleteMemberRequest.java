package com.example.financialcheat.model.dto.project;

import lombok.Data;

@Data
public class DeleteMemberRequest {

    private Integer projectId;

    private Integer userId;
}
