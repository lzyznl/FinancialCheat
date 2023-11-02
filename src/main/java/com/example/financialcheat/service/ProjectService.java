package com.example.financialcheat.service;

import com.example.financialcheat.model.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【Project(用户)】的数据库操作Service
* @createDate 2023-11-02 15:28:51
*/
public interface ProjectService extends IService<Project> {
    /**
     * 用户添加新的项目
     * @param projectName 项目名称
     * @param position 该用户在项目中的职位
     * @return
     */
    boolean addProject(String projectName, int position, HttpServletRequest request);

}
