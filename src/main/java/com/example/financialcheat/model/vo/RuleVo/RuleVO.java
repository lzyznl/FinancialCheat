package com.example.financialcheat.model.vo.RuleVo;

import lombok.Data;

import java.util.List;

@Data
public class RuleVO {

    private String username;

    private Integer ruleId;

    private List<String> ruleSet;

    private String createTime;

    private String updateTime;

    private Integer status;
}
