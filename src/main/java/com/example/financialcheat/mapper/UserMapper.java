package com.example.financialcheat.mapper;

import com.example.financialcheat.model.entity.User;
import com.example.financialcheat.model.vo.MemberVo.MemberVO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【User(用户)】的数据库操作Mapper
* @createDate 2023-11-01 20:49:12
* @Entity com.example.financialcheat.model.entity.User
*/
public interface UserMapper extends MPJBaseMapper<User> {

    List<MemberVO> getMembersByProjectId(@Param("projectId")Integer projectId);
}




