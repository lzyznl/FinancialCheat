package com.example.financialcheat.service;

import cn.hutool.json.JSONObject;
import com.example.financialcheat.model.entity.Rules;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialcheat.model.vo.RuleVo.RuleHistoryVo;
import com.example.financialcheat.model.vo.RuleVo.RuleVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【Rules(用户已经创建的规则集)】的数据库操作Service
* @createDate 2023-11-05 17:24:29
*/
public interface RulesService extends IService<Rules> {

    /**
     * 新增一条规则
     *
     * @param
     * @param fileId
     * @param projectId
     * @param rule
     * @return
     */
    Integer addNewRule( Long fileId,Long projectId, String rule, HttpServletRequest request);

    /**
     * 修改规则
     *
     * @param ruleId
     * @param rule
     * @param ruleName
     * @return
     */
    boolean updateRule(Integer ruleId,String rule,String ruleName);


    /**
     * 根据项目id获取与之相关的所有规则的状态
     * @param projectId
     * @return
     */
    List<RuleVO> getAllRuleStatusByProjectId(Integer projectId);

    /**
     * 启用/禁用规则的状态
     * @param projectId
     * @param ruleId
     * @param status
     * @return
     */
    Boolean changeRuleStatus(long projectId,long ruleId,int status);


    /**
     * 查看规则的历史记录
     * @param ruleId
     * @param projectId
     * @return
     */
    List<RuleHistoryVo> getHistory(long ruleId,long projectId);


    /**
     * 添加规则的历史记录
     * @param projectId
     * @param ruleId
     * @param event
     * @param userName
     * @return
     */
    Boolean addHistory(long projectId,long ruleId,String event,String userName);


    /**
     * 删除规则
     * @param projectId
     * @param ruleId
     * @return
     */
    Boolean deleteRule(long projectId,long ruleId);

    /**
     * 运行规则或者规则集
     * @param type
     * @param id
     * @return
     */
    List<String> runRules(Integer type,Long id);

    /**
     * 组件，运行某一条规则
     * @param rule
     * @return
     */
    String runOneRule(Rules rule);

    /**
     * 根据项目id按类型获取这个项目下的所有库
     *
     * @param projectId
     * @return
     */
    JSONObject getFileByProjectId(Long projectId, Integer type);
}
