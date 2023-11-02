package com.example.financialcheat.model.relationShip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户和项目间的关系
 * @TableName UserProjectRelationShip
 */
@TableName(value ="UserProjectRelationShip")
@Data
public class UserProjectRelationShip implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long projectId;

    /**
     * 用户在该项目的职位
0 - 队员
1 - 队长
     */
    private Integer position;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}