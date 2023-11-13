package com.example.financialcheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.mapper.RulesMapper;
import com.example.financialcheat.model.entity.Rules;
import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.example.financialcheat.model.relationShip.RuleFileRelationShip;
import com.example.financialcheat.service.FileRelationShipService;
import com.example.financialcheat.mapper.FileRelationShipMapper;
import com.example.financialcheat.service.RuleFileRelationShipService;
import com.example.financialcheat.service.RulesService;
import com.example.financialcheat.service.VariableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 宇宙无敌超级大帅哥
 * @description 针对表【FileRelationShip(文件关系表)】的数据库操作Service实现
 * @createDate 2023-11-05 17:24:29
 */
@Service
public class FileRelationShipServiceImpl extends ServiceImpl<FileRelationShipMapper, FileRelationShip>
        implements FileRelationShipService {


    @Resource
    private RulesMapper rulesMapper;

    @Resource
    private VariableService variableService;

    @Resource
    private RuleFileRelationShipService ruleFileRelationShipService;

    @Override
    public boolean createFile(Integer isFolder, Long parentId, String fileName, Integer fileType, Long projectId) {
        FileRelationShip ship = new FileRelationShip();
        ship.setParentId(parentId);
        ship.setFileName(fileName);
        ship.setIsFolder(isFolder);
        ship.setFileType(fileType);
        ship.setProjectId(projectId);
        return this.save(ship);
    }

    @Override
    public boolean deleteFile(Long fileId) {
        FileRelationShip ship = this.getById(fileId);
        Integer fileType = ship.getFileType();
        this.removeById(fileId);
        if(fileType==0){//删除变量
            QueryWrapper wrapper0=new QueryWrapper<>();
            wrapper0.eq("fileId",fileId);
            return variableService.remove(wrapper0);
        }else if (fileType==1){//删除规则
            RuleFileRelationShip one = ruleFileRelationShipService
                    .getOne(new QueryWrapper<RuleFileRelationShip>().eq("fileId", fileId));
            rulesMapper.deleteById(one.getRuleId());
            QueryWrapper<RuleFileRelationShip> wrapper = new QueryWrapper<>();
            wrapper.eq("ruleId", one.getRuleId());
            ruleFileRelationShipService.remove(wrapper);
            return true;
        }else {//删除决策集
            QueryWrapper<RuleFileRelationShip> wrapper = new QueryWrapper<>();
            wrapper.eq("setId", fileId);
            return ruleFileRelationShipService.remove(wrapper);
        }
    }
}




