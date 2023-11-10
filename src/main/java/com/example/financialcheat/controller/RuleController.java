package com.example.financialcheat.controller;


import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.rule.*;
import com.example.financialcheat.model.vo.RuleVo.RuleHistoryVo;
import com.example.financialcheat.model.vo.RuleVo.RuleVO;
import com.example.financialcheat.service.RulesService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "规则管理器")
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private RulesService rulesService;



    @PostMapping("/add")
    public BaseResponse<Integer> addRule(@RequestBody GetRuleAddRequest getRuleAddRequest, HttpServletRequest request) {
        Long fileId = getRuleAddRequest.getFileId();
        String rule = getRuleAddRequest.getRule();
        Long projectId = getRuleAddRequest.getProjectId();
        if (StringUtils.isBlank(rule)  || fileId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer ruleId = rulesService.addNewRule(fileId,projectId , rule, request);
        return ResultUtils.success(ruleId,"新增成功！");
    }

    @PostMapping("/update")
    public BaseResponse<String> updateRule(@RequestBody UpdateRuleRequest request){
        if (request==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer ruleId = request.getRuleId();
        String rule = request.getRule();
        String ruleName = request.getRuleName();
        return rulesService.updateRule(ruleId,rule,ruleName)?
                ResultUtils.success("修改成功！"):ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @GetMapping("/ruleStatus/get")
    public BaseResponse<List<RuleVO>> getAllRuleStatus(@RequestParam Integer projectId){
        if(projectId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<RuleVO> ruleVOS = rulesService.getAllRuleStatusByProjectId(projectId);
        return ResultUtils.success(ruleVOS,"获取规则列表成功");
    }

    @PostMapping("/ruleStatus/change")
    public BaseResponse<Boolean> changeRulesStatus(@RequestBody RuleStatusChangeRequest ruleStatusChangeRequest){
        long projectId = ruleStatusChangeRequest.getProjectId();
        long ruleId = ruleStatusChangeRequest.getRuleId();
        int status = ruleStatusChangeRequest.getStatus();
        if(projectId<0||ruleId<0||status<0||status>1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean fact = rulesService.changeRuleStatus(projectId, ruleId, status);
        return ResultUtils.success(fact);
    }

    @GetMapping("/ruleHistory/get")
    public BaseResponse<List<RuleHistoryVo>> getRulesHistory(@RequestBody GetRuleHistoryRequest ruleHistoryRequest){
        long ruleId = ruleHistoryRequest.getRuleId();
        long projectId = ruleHistoryRequest.getProjectId();
        if(projectId<0||ruleId<-1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<RuleHistoryVo> historyList = rulesService.getHistory(ruleId, projectId);
        return ResultUtils.success(historyList,"获取规则历史记录成功");
    }

    @PostMapping("/ruleHistory/add")
    public BaseResponse<Boolean> addHistory(@RequestBody AddRuleHistoryRequest addRuleHistoryRequest){
        long ruleId = addRuleHistoryRequest.getRuleId();
        long projectId = addRuleHistoryRequest.getProjectId();
        String event = addRuleHistoryRequest.getEvent();
        String userName = addRuleHistoryRequest.getUserName();
        if(ruleId<0||projectId<0||StringUtils.isAnyBlank(event,userName)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean fact = rulesService.addHistory(projectId, ruleId, event, userName);
        return ResultUtils.success(fact,"添加规则历史记录成功");
    }

    @GetMapping("/ruleStatus/delete")
    public BaseResponse<Boolean> deleteHistory(@RequestBody DeleteRuleRequest deleteRuleRequest){
        long ruleId = deleteRuleRequest.getRuleId();
        long projectId = deleteRuleRequest.getProjectId();
        if(ruleId<0||projectId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean fact = rulesService.deleteRule(projectId, ruleId);
        return ResultUtils.success(fact,"删除规则成功");
    }

    @PostMapping("/run")
    public BaseResponse<List<String>> runRules(@RequestBody RuleRunRequest ruleRunRequest){
        if(ruleRunRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer type = ruleRunRequest.getType();
        Long id = ruleRunRequest.getId();
        if(type<0||id<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<String> ans = rulesService.runRules(type, id);
        return ResultUtils.success(ans);
    }
}
