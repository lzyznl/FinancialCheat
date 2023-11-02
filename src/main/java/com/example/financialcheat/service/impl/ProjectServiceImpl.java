package com.example.financialcheat.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.model.entity.Project;
import com.example.financialcheat.model.entity.User;
import com.example.financialcheat.model.relationShip.UserProjectRelationShip;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.ProjectService;
import com.example.financialcheat.mapper.ProjectMapper;
import com.example.financialcheat.service.UserProjectRelationShipService;
import com.example.financialcheat.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.example.financialcheat.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 宇宙无敌超级大帅哥
 * @description 针对表【Project(用户)】的数据库操作Service实现
 * @createDate 2023-11-02 15:28:51
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
        implements ProjectService {
    
    @Resource
    private ProjectMapper projectMapper;


    @Resource
    private UserService userService;

    @Resource
    private UserProjectRelationShipService userProjectRelationShipService;

    @Override
    public boolean addProject(String projectName, int position, HttpServletRequest request) {
        //先插入项目
        Project project = new Project();
        project.setProjectName(projectName);
        int isInsert = projectMapper.insert(project);
        if(isInsert==0){
            return false;
        }
        //insert方法可以返回id
        Long projectId = project.getId();
        //查询用户id
        SafetyUser user = (SafetyUser) request.getSession().getAttribute(USER_LOGIN_STATE);
        Long userId = user.getUserId();
        UserProjectRelationShip userProjectRelationShip = new UserProjectRelationShip();
        userProjectRelationShip.setUserId(userId);
        userProjectRelationShip.setProjectId(projectId);
        userProjectRelationShip.setPosition(position);
        //保存用户和项目的关系
        boolean isSave = userProjectRelationShipService.save(userProjectRelationShip);
        if(!isSave){
            return false;
        }
        return true;
    }
}




