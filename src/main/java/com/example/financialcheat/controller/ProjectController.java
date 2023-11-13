package com.example.financialcheat.controller;

import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.DeleteRequest;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.project.DeleteMemberRequest;
import com.example.financialcheat.model.dto.project.ProjectAddRequest;
import com.example.financialcheat.model.vo.MemberVo.MemberVO;
import com.example.financialcheat.model.vo.ProjectVo.ProjectVO;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.ProjectService;
import com.example.financialcheat.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "项目管理器")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<String> addNewProject(@RequestBody ProjectAddRequest projectAddRequest, HttpServletRequest request) {
        if (projectAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String projectName = projectAddRequest.getProjectName();
        int position = projectAddRequest.getPosition();
        if (StringUtils.isBlank(projectName) || position <= -1) {
            return null;
        }
        return projectService.addProject(projectName, position, request) ?
                ResultUtils.success("新增成功！") : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PostMapping("/delete")
    public BaseResponse<String> deleteProject(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long projectId = deleteRequest.getId();
        return projectService.delProject(projectId,request)?
                ResultUtils.success("删除成功！"):ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PostMapping("/get")
    public BaseResponse<List<ProjectVO>> getProjects(HttpServletRequest request){
        SafetyUser user = userService.getLoginUser(request);
        if(user==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        Long userId = user.getUserId();
        List<ProjectVO> projects = projectService.getProjects(userId);
        return ResultUtils.success(projects);
    }

    @GetMapping("/memebers")
    public BaseResponse<List<MemberVO>> getMembers(@RequestParam("projectId")Integer projectId){
        if (projectId<0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        List<MemberVO> members = projectService.getMembers(projectId);
        return ResultUtils.success(members);
    }

    @PostMapping("/deleteMember")
    public BaseResponse<String> deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest){
        if(deleteMemberRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer projectId = deleteMemberRequest.getProjectId();
        Integer userId = deleteMemberRequest.getUserId();
        if(projectId<0||userId<0){
            return null;
        }
        return projectService.deleteUserProjectRelationShip(projectId,userId)?
                ResultUtils.success("删除成功！"):ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


}
