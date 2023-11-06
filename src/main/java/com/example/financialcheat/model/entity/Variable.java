package com.example.financialcheat.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Variable
 */
@TableName(value ="Variable")
@Data
public class Variable implements Serializable {
    /**
     * 变量表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 变量名
     */
    private String variableName;

    /**
     * 变量类型
     */
    private String variableType;

    /**
     * 变量描述
     */
    private String description;

    /**
     * 变量默认值
     */
    private String value;

    /**
     * 文件id
     */
    private Long fileId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}