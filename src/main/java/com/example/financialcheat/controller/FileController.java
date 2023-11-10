package com.example.financialcheat.controller;


import cn.hutool.json.JSONObject;
import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.file.Hub;
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
}
