package com.example.financialcheat.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialcheat.mapper.UserMapper;
import com.example.financialcheat.mapper.UserProjectRelationShipMapper;
import com.example.financialcheat.model.entity.Project;
import com.example.financialcheat.model.relationShip.UserProjectRelationShip;
import com.example.financialcheat.model.vo.MemberVo.MemberVO;
import com.example.financialcheat.model.vo.ProjectVo.ProjectVO;
import com.example.financialcheat.model.vo.SafetyUser;
import com.example.financialcheat.service.ProjectService;
import com.example.financialcheat.mapper.ProjectMapper;
import com.example.financialcheat.service.UserProjectRelationShipService;
import com.example.financialcheat.service.UserService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
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
    private UserMapper userMapper;

    @Resource
    private UserProjectRelationShipMapper userProjectRelationShipMapper;

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

    @Override
    public boolean delProject(Long projectId,HttpServletRequest request) {
        SafetyUser user = userService.getLoginUser(request);
        Long userId = user.getUserId();
        boolean isDel = this.update()
                .eq("id", projectId)
                .set("isDelete", 1)
                .update();
        if(!isDel){
            return false;
        }
        return userProjectRelationShipService.update()
                .eq("userId", userId)
                .eq("projectId", projectId)
                .set("isDelete", 1)
                .update();
    }

    @Override
    public List<ProjectVO> getProjects(Long userId) {
        MPJLambdaWrapper<Project> wrapper = new MPJLambdaWrapper<Project>()
                .select(Project::getId, Project::getProjectName)
                .select(UserProjectRelationShip::getPosition)
                .leftJoin(UserProjectRelationShip.class, UserProjectRelationShip::getProjectId, Project::getId)
                .eq(UserProjectRelationShip::getUserId, userId);
        return projectMapper.selectJoinList(ProjectVO.class, wrapper);
    }

    @Override
    public List<MemberVO> getMembers(Integer projectId) {
        return userMapper.getMembersByProjectId(projectId);
    }

    @Override
    public boolean deleteUserProjectRelationShip(Integer projectId, Integer userId) {
        QueryWrapper<UserProjectRelationShip> wrapper=new QueryWrapper<>();
        wrapper.eq("projectId",projectId);
        wrapper.eq("userId",userId);
        long isExist = userProjectRelationShipService.count(wrapper);
        if(isExist==0){
            return false;
        }
        return userProjectRelationShipService.update()
                .eq("projectId", projectId)
                .eq("userId", userId)
                .set("isDelete", 1)
                .update();
    }


}




