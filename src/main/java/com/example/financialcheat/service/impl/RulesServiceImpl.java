package com.example.financialcheat.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.common.ErrorCode;
import com.example.financialcheat.exception.BusinessException;
import com.example.financialcheat.mapper.UserMapper;
import com.example.financialcheat.model.entity.Rules;
import com.example.financialcheat.model.entity.Ruleshistory;
import com.example.financialcheat.model.entity.User;
import com.example.financialcheat.model.entity.Variable;
import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.example.financialcheat.model.relationShip.RuleFileRelationShip;
import com.example.financialcheat.model.vo.RuleVo.RuleHistoryVo;
import com.example.financialcheat.model.vo.RuleVo.RuleVO;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.*;
import com.example.financialcheat.mapper.RulesMapper;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
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

    @Resource
    private VariableService variableService;

    @Resource
    private RuleFileRelationShipService ruleFileRelationShipService;


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
        rules.setProjectId(projectId);
        rulesMapper.insert(rules);
        RuleFileRelationShip ship = new RuleFileRelationShip();
        ship.setRuleId((long) rules.getId());
        ship.setFileId(fileId);
        ruleFileRelationShipService.save(ship);
        return rules.getId();
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
            List<RuleFileRelationShip> ruleFileShip = ruleFileRelationShipService.query()
                    .eq("ruleId", rule.getId())
                    .list();
            List<Long> numbers = ruleFileShip.stream()
                    .map(RuleFileRelationShip::getFileId)
                    .collect(Collectors.toList());
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
        return delete >= 1;
    }

    @Override
    public List<String> runRules(Integer type, Long id) {
        List<String> ans = new ArrayList<>();
        switch (type) {
            case 0://单条规则
                Rules rule = this.getById(id);
                ans.add(runOneRule(rule));
            case  1://一组规则
                List<Rules> rules = rulesMapper.selectList(null);



        }
        return ans;

    }

    @Override
    public String runOneRule(Rules rule) {
        String ruleBody = rule.getRule();
        StringBuilder decodingRuleBody = new StringBuilder();
        int flag = 0;
        StringBuilder tempStr = new StringBuilder();
        MapContext mapContext = new MapContext();
        for (int i = 0; i < ruleBody.length(); ++i) {
            if (ruleBody.charAt(i) == '#' || flag == 1 || flag == 3) {
                flag += 1;
                tempStr.append(ruleBody.charAt(i));
                if (flag == 5) {
                    int number;
                    String letter;
                    String result = tempStr.toString();
                    result = result.substring(1);
                    // 按#字符分割字符串
                    String[] parts = result.split("#");
                    number = Integer.parseInt(parts[0]);
                    letter = parts[1];
                    //重置
                    flag = 0;
                    tempStr.setLength(0);
                    //开始查数据库的变量
                    Variable var = variableService.query()
                            .eq("fileId",number)
                            .eq("variableName",letter)
                            .one();
                    mapContext.set(letter,Integer.parseInt(var.getValue()));
                    decodingRuleBody.append(letter);
                }
            } else {
                decodingRuleBody.append(ruleBody.charAt(i));
            }
        }
        JexlBuilder jexlBuilder = new JexlBuilder();
        JexlEngine jexlEngine = jexlBuilder.create();
        JexlScript script = jexlEngine.createScript(String.valueOf(decodingRuleBody));
        Object execute = script.execute(mapContext);
        return execute.toString();
    }

    public static JSONArray convertToJsonArray(List<FileRelationShip> nodes, Map<Integer, List<FileRelationShip>> nodeMap) {
        JSONArray jsonArray = JSONUtil.createArray();
        for (FileRelationShip node : nodes) {
            JSONObject jsonNode = JSONUtil.createObj()
                    .set("id", node.getId())
                    .set("fileName", node.getFileName())
                    .set("isFolder", node.getIsFolder() == 1);
            List<FileRelationShip> children = nodeMap.get(Math.toIntExact(node.getId()));
            if (children != null && !children.isEmpty()) {
                jsonNode.set("children", convertToJsonArray(children, nodeMap));
            }
            jsonArray.add(jsonNode);
        }
        return jsonArray;
    }

    public static JSONObject convertToJSONTree(List<FileRelationShip> fileNodes) {
        List<FileRelationShip> rootNodes = new ArrayList<>();

        // Group nodes by their parent ID
        Map<Integer, List<FileRelationShip>> nodeMap = new HashMap<>();
        for (FileRelationShip node : fileNodes) {
            int parentId = Math.toIntExact(node.getParentId());
            if (!nodeMap.containsKey(parentId)) {
                nodeMap.put(parentId, new ArrayList<>());
            }
            nodeMap.get(parentId).add(node);
        }

        // Find root nodes
        for (FileRelationShip node : fileNodes) {
            if (node.getParentId() == 0) {
                rootNodes.add(node);
            }
        }

        // Convert to JSON
        JSONObject jsonTree = new JSONObject();
        jsonTree.set("data", convertToJsonArray(rootNodes, nodeMap));
        return jsonTree;
    }

    @Override
    public JSONObject getFileByProjectId(Long projectId, Integer type) {
        List<FileRelationShip> allList = fileRelationShipService.query()
                .eq("projectId", projectId)
                .eq("fileType", type)
                .list();
        return convertToJSONTree(allList);
    }

}




