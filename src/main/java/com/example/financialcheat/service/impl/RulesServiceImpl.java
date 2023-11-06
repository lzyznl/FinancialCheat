package com.example.financialcheat.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.mapper.UserMapper;
import com.example.financialcheat.model.entity.Rules;
import com.example.financialcheat.model.entity.Ruleshistory;
import com.example.financialcheat.model.entity.User;
import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.example.financialcheat.model.vo.RuleVo.RuleHistoryVo;
import com.example.financialcheat.model.vo.RuleVo.RuleVO;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.*;
import com.example.financialcheat.mapper.RulesMapper;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【Rules(用户已经创建的规则集)】的数据库操作Service实现
* @createDate 2023-11-05 17:24:29
*/
@Service
public class RulesServiceImpl extends ServiceImpl<RulesMapper, Rules>
    implements RulesService{

    @Resource
    private UserService userService;
    @Resource
    private RulesMapper rulesMapper;

    @Resource
    private RuleshistoryService ruleshistoryService;


    @Resource
    private FileRelationShipService fileRelationShipService;


    @Override
    public Integer addNewRule(Long fileId, Long projectId, String rule, HttpServletRequest request) {
        SafetyUser loginUser = userService.getLoginUser(request);
        if(loginUser==null){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Long userId = loginUser.getUserId();

        Rules rules = new Rules();
        rules.setUserId(Math.toIntExact(userId));
        rules.setRule(rule);
        rules.setFileIdSet(fileId+"@");

        return rulesMapper.insert(rules);
    }

    @Override
    public List<RuleVO> getAllRuleStatusByProjectId(Integer projectId) {
        List<RuleVO> ruleVOS=new ArrayList<>();
        List<Rules> rules = this.query()
                .eq("projectId", projectId)
                .list();

        for(Rules rule:rules){
            RuleVO ruleVO = new RuleVO();

            ruleVO.setRuleId(rule.getId());
            ruleVO.setCreateTime(DateUtil.format(rule.getCreateTime(),"yyyy-MM-dd"));
            ruleVO.setUpdateTime(DateUtil.format(rule.getUpdateTime(),"yyyy-MM-dd"));
            ruleVO.setStatus(rule.getStatus());

            User user = userService.query()
                    .eq("id", rule.getUserId())
                    .one();
            ruleVO.setUsername(user.getUserAccount());
            String[] parts = rule.getFileIdSet().split("@");
            List<Integer> numbers = new ArrayList<>();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    int number = Integer.parseInt(part);
                    numbers.add(number);
                }
            }
            List<FileRelationShip> ships = fileRelationShipService.query()
                    .in("id", numbers)
                    .list();
            List<String> fileNames=new ArrayList<>();
            for (FileRelationShip ship:ships){
                fileNames.add(ship.getFileName());
            }
            ruleVO.setRuleSet(fileNames);
            ruleVOS.add(ruleVO);
        }
        return ruleVOS;
    }

    @Override
    public Boolean changeRuleStatus(long projectId, long ruleId, int status) {
        QueryWrapper<Rules> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",ruleId);
        queryWrapper.eq("projectId",projectId);
        Rules rules = rulesMapper.selectOne(queryWrapper);
        rules.setStatus(status);
        int i = rulesMapper.updateById(rules);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<RuleHistoryVo> getHistory(long ruleId, long projectId) {
        // 定义输出格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<RuleHistoryVo> ruleHistoryVoList = new ArrayList<>();
        QueryWrapper<Ruleshistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("projectId",projectId);
        if(ruleId!=-1){
            queryWrapper.eq("ruleId",ruleId);
        }
        List<Ruleshistory> historyList = ruleshistoryService.list(queryWrapper);
        historyList.forEach(ruleshistory -> {
            RuleHistoryVo ruleHistoryVo = new RuleHistoryVo();
            ruleHistoryVo.setUserName(ruleshistory.getUserName());
            ruleHistoryVo.setTime(outputFormat.format(ruleshistory.getCreateTime()));
            ruleHistoryVo.setEvent(ruleshistory.getEvent());
            ruleHistoryVoList.add(ruleHistoryVo);
        });
        return ruleHistoryVoList;
    }

    @Override
    public Boolean addHistory(long projectId, long ruleId, String event, String userName) {
        Ruleshistory ruleshistory = new Ruleshistory();
        ruleshistory.setRuleId(ruleId);
        ruleshistory.setEvent(event);
        ruleshistory.setUserName(userName);
        ruleshistory.setProjectId(projectId);
        return ruleshistoryService.save(ruleshistory);
    }

    @Override
    public Boolean deleteRule(long projectId, long ruleId) {
        QueryWrapper<Rules> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",ruleId);
        queryWrapper.eq("projectId",projectId);
        int delete = rulesMapper.delete(queryWrapper);
        if(delete>=1){
            return true;
        }else{
            return false;
        }
    }
}




