package com.example.financialcheat.controller;


import cn.hutool.json.JSONObject;
import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.file.CreateFileRequest;
import com.example.financialcheat.model.dto.file.Hub;
import com.example.financialcheat.service.FileRelationShipService;
import com.example.financialcheat.service.RulesService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "文件管理器")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private RulesService rulesService;

    @Resource
    private FileRelationShipService fileRelationShipService;


    @PostMapping("/getTree")
    public BaseResponse<JSONObject> getFileHub(@RequestBody Hub hub){
        if(hub==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long projectId = hub.getProjectId();
        Integer type = hub.getType();
        if(projectId<0||type<0){
            return null;
        }
        JSONObject jsonObject = rulesService.getFileByProjectId(projectId, type);
        return ResultUtils.success(jsonObject);
    }

    @PostMapping("/createFile")
    public BaseResponse<String> createFile(@RequestBody CreateFileRequest request){


        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer isFolder = request.getIsFolder();
        Long parentId = request.getParentId();
        String fileName = request.getFileName();
        Integer fileType = request.getFileType();
        Long projectId = request.getProjectId();
        return fileRelationShipService.createFile(isFolder,parentId,fileName,fileType,projectId)?
                ResultUtils.success("新建成功！") : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

}
