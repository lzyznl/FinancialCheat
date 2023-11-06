package com.example.financialcheat.model.relationShip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 文件关系表
 * @TableName FileRelationShip
 */
@TableName(value ="FileRelationShip")
@Data
public class FileRelationShip implements Serializable {
    /**
     * 文件表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父文件id
0 代表这个文件没有父文件
     */
    private Long parentId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private Integer isFolder;

    /**
     * 文件类型
     */
    private Integer fileType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}