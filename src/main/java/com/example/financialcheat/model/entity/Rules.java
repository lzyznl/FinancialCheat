package com.example.financialcheat.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户已经创建的规则集
 * @TableName Rules
 */
@TableName(value ="Rules")
@Data
public class Rules implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private String rule;


    private String ruleName;


    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 从属于某个project
     */
    private Long projectId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0 - 关闭
1 - 开启
默认开启
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}