package com.example.financialcheat.service;

import com.example.financialcheat.model.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialcheat.model.vo.MemberVo.MemberVO;
import com.example.financialcheat.model.vo.ProjectVo.ProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 根据项目id删除项目，顺便把对应的关系也删了
     * @param projectId
     * @return
     */
    boolean delProject(Long projectId,HttpServletRequest request);

    /**
     * 获取用户参与的所有项目
     * @param userId
     * @return
     */
    List<ProjectVO> getProjects(Long userId);

    /**
     * 获取项目里的所有成员
     * @param projectId
     * @return
     */
    List<MemberVO> getMembers(Integer projectId);

    /**
     * 删除projectId和userId的对应关系
     * @param projectId
     * @param userId
     * @return
     */
    boolean deleteUserProjectRelationShip(Integer projectId,Integer userId);

}
