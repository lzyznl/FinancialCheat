package com.example.financialcheat.model.relationShip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 规则和库的关系
 * @TableName RuleFileRelationShip
 */
@Data
@TableName(value ="RuleFileRelationShip")
public class RuleFileRelationShip implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long ruleId;

    /**
     *
     */
    private Long fileId;

    private long setId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}