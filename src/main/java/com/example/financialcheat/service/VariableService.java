package com.example.financialcheat.service;

import com.example.financialcheat.model.entity.Variable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialcheat.model.vo.VariableVo.variableListVo;

import java.util.List;

/**
 * @author 宇宙无敌超级大帅哥
 * @description 针对表【Variable】的数据库操作Service
 * @createDate 2023-11-05 17:24:29
 */
public interface VariableService extends IService<Variable> {

    /**
     * 获取某个变量库的所有变量
     *
     * @param fileId
     * @return
     */
    List<variableListVo> variableList(long fileId);


    /**
     * 新增变量
     * @param variableName
     * @param variableType
     * @param description
     * @param value
     * @param fileId
     * @return
     */
    boolean add(String variableName, String variableType, String description, String value,
                Long fileId);


    /**
     * 更新变量
     * @param variableName
     * @param variableType
     * @param description
     * @param value
     * @return
     */
    boolean update(Long id,String variableName, String variableType, String description, String value);
}
