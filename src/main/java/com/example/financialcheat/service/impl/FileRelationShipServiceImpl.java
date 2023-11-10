package com.example.financialcheat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.model.relationShip.FileRelationShip;
import com.example.financialcheat.service.FileRelationShipService;
import com.example.financialcheat.mapper.FileRelationShipMapper;
import org.springframework.stereotype.Service;

/**
 * @author 宇宙无敌超级大帅哥
 * @description 针对表【FileRelationShip(文件关系表)】的数据库操作Service实现
 * @createDate 2023-11-05 17:24:29
 */
@Service
public class FileRelationShipServiceImpl extends ServiceImpl<FileRelationShipMapper, FileRelationShip>
        implements FileRelationShipService {

    @Override
    public boolean createFile(Integer isFolder, Long parentId, String fileName, Integer fileType, Long projectId) {
        FileRelationShip ship = new FileRelationShip();
        ship.setParentId(parentId);
        ship.setFileName(fileName);
        ship.setIsFolder(isFolder);
        ship.setFileType(fileType);
        ship.setProjectId(projectId);
        return this.save(ship);
    }
}




