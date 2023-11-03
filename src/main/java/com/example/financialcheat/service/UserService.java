package com.example.financialcheat.service;

import com.example.financialcheat.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialcheat.model.vo.SafetyUser;

import javax.servlet.http.HttpServletRequest;

/**
* @author 宇宙无敌超级大帅哥
* @description 针对表【User(用户)】的数据库操作Service
* @createDate 2023-11-01 20:49:12
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    boolean userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    boolean userRegister(String userAccount, String userPassword, String checkPassword);

    SafetyUser getSafetyUser(User user);

    SafetyUser getLoginUser(HttpServletRequest request);

}
