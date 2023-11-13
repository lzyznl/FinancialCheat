package com.example.financialcheat.model.relationShip;

import com.baomidou.mybatisplus.annotation.*;

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

    private int position;

    private String deleteTime;
    /**
     * 0 - 未删除
     1 - 已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}