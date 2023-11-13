package com.example.financialcheat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.financialcheat.common.BaseResponse;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.common.ResultUtils;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.model.dto.Variable.AddVariableRequest;
import com.example.financialcheat.model.dto.Variable.GetVariableListRequest;
import com.example.financialcheat.model.dto.Variable.UpdateVariableRequest;
import com.example.financialcheat.model.entity.Variable;
import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.example.financialcheat.model.vo.VariableVo.variableListVo;
import com.example.financialcheat.service.FileRelationShipService;
import com.example.financialcheat.service.VariableService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "变量管理器")
@RestController
@RequestMapping("/variable")
public class VaribaleController {


    @Resource
    private VariableService variableService;

    @Resource
    private FileRelationShipService fileRelationShipService;

    @PostMapping("/get")
    public BaseResponse<List<variableListVo>> getVariableList(@RequestBody GetVariableListRequest getVariableListRequest) {
        long fileId = getVariableListRequest.getFileId();
        if (fileId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<variableListVo> list = variableService.variableList(fileId);
        return ResultUtils.success(list, "获取变量成功");
    }

    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteVariable(@RequestParam("variableId") long variableId) {
        if (variableId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return variableService.remove(new QueryWrapper<Variable>().eq("id", variableId)) ? ResultUtils.success(true, "删除成功") : ResultUtils.error(500, "删除失败");
    }

    @GetMapping("/deleteSet")
    public BaseResponse<Boolean> deleteSet(@RequestParam("fileId") long fileId) {
        if (fileId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return fileRelationShipService.remove(new QueryWrapper<FileRelationShip>().eq("id", fileId)) ? ResultUtils.success(true, "删除成功") : ResultUtils.error(500, "删除失败");
    }


    @PostMapping("/add")
    public BaseResponse<String> add(@RequestBody AddVariableRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String variableName = request.getVariableName();
        String variableType = request.getVariableType();
        String description = request.getDescription();
        String value = request.getValue();
        Long fileId = request.getFileId();
        return variableService.add(variableName, variableType, description, value, fileId) ?
                ResultUtils.success("新增成功！") : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @PostMapping("/update")
    public BaseResponse<String> update(@RequestBody UpdateVariableRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = request.getId();
        String variableName = request.getVariableName();
        String variableType = request.getVariableType();
        String description = request.getDescription();
        String value = request.getValue();
        return variableService.update(id,variableName,variableType,description,value)?
                ResultUtils.success("修改成功！") : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

}
