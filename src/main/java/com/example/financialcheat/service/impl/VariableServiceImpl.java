package com.example.financialcheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.model.entity.Variable;
import com.example.financialcheat.model.vo.VariableVo.variableListVo;
import com.example.financialcheat.service.VariableService;
import com.example.financialcheat.mapper.VariableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【Variable】的数据库操作Service实现
* @createDate 2023-11-05 17:24:29
*/
@Service
public class VariableServiceImpl extends ServiceImpl<VariableMapper, Variable>
    implements VariableService{

    @Resource
    private VariableMapper variableMapper;


    @Override
    public List<variableListVo> variableList(long fileId) {
        List<variableListVo> variableListVoList = new ArrayList<>();
        variableListVo variableListVo = new variableListVo();
        List<Variable> variableList = variableMapper.selectList(new QueryWrapper<Variable>().eq("fileId", fileId));
        variableList.forEach(variable -> {
            variableListVo.setVariableName(variable.getVariableName());
            variableListVo.setVariableType(variable.getVariableType());
            variableListVo.setDescription(variable.getDescription());
            variableListVo.setValue(variable.getValue());
            variableListVo.setId(variable.getId());
            variableListVoList.add(variableListVo);
        });
        return variableListVoList;
    }
}




