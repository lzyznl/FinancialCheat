package com.example.financialcheat.service;

import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【FileRelationShip(文件关系表)】的数据库操作Service
* @createDate 2023-11-05 17:24:29
*/
public interface FileRelationShipService extends IService<FileRelationShip> {
    /**
     * 创建文件或者文件夹
     * @param isFolder
     * @param parentId
     * @param fileName
     * @param fileType
     * @param projectId
     * @return
     */
    boolean createFile(Integer isFolder,Long parentId,String fileName,Integer fileType,Long projectId);
}
