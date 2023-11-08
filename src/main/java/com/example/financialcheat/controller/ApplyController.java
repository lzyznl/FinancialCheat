package com.example.financialcheat.controller;

import cn.hutool.http.HttpRequest;
import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.applyRequest.handleApplyRequest;
import com.example.financialcheat.model.vo.ApplyVo.applyListVo;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.ApplyhistoryService;
import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public BaseResponse<List<applyListVo>> getApplyList(@RequestParam Long projectId){
        if(projectId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<applyListVo> applyListVoList = applyhistoryService.applyList(projectId);
        return ResultUtils.success(applyListVoList,"获取申请列表成功");
    }


    @PostMapping("/handle")
    public BaseResponse<Boolean> handleApply(@RequestBody handleApplyRequest handleApplyRequest){
        long projectId = handleApplyRequest.getProjectId();
        int isApply = handleApplyRequest.getIsApply();
        long applyId = handleApplyRequest.getApplyId();
        if(applyId<0||projectId<0||(isApply!=1&&isApply!=2)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean fact = applyhistoryService.passOrRejectApply(projectId,applyId,isApply);
        return fact?ResultUtils.success(fact,"处理成功"):ResultUtils.error(500,"处理失败");
    }
}
