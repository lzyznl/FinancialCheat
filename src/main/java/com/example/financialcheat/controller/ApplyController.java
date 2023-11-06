package com.example.financialcheat.controller;

import cn.hutool.http.HttpRequest;
import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.entity.User;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.ApplyhistoryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.example.financialcheat.constant.UserConstant.USER_LOGIN_STATE;

@Api(tags = "申请管理器")
@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Resource
    private ApplyhistoryService applyhistoryService;


    @GetMapping("/add")
    public BaseResponse<Boolean> addApply(@RequestParam Long projectId, HttpServletRequest request){
        if(projectId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SafetyUser loginUser = (SafetyUser) request.getSession().getAttribute(USER_LOGIN_STATE);
        if(loginUser==null){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Long userId = loginUser.getUserId();
        String userName = loginUser.getUserAccount();
        Boolean fact = applyhistoryService.addApplyToProject(projectId, userId, userName);
        return fact? ResultUtils.success(fact,"申请已提交"):ResultUtils.error(500,"系统内部错误，申请未成功");
    }

}
