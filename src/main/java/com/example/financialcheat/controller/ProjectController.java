package com.example.financialcheat.controller;

import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.project.ProjectAddRequest;
import com.example.financialcheat.service.ProjectService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "项目管理器")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping("/add")
    public BaseResponse<String> addNewProject(@RequestBody ProjectAddRequest projectAddRequest, HttpServletRequest request){
        if(projectAddRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String projectName = projectAddRequest.getProjectName();
        int position = projectAddRequest.getPosition();
        if(StringUtils.isBlank(projectName)){
            return null;
        }
        return projectService.addProject(projectName,position,request)?
                ResultUtils.success("新增成功！"):ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}
