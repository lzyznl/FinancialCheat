package com.example.financialcheat.model.dto.file;


import lombok.Data;

@Data
public class CreateFileRequest {
    private Integer isFolder;
    private Long parentId;
    private String fileName;
    private Integer fileType;
    private Long projectId;
}
