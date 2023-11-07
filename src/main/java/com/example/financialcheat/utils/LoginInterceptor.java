package com.example.financialcheat.utils;

import com.example.financialcheat.model.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.financialcheat.constant.UserConstant.USER_LOGIN_STATE;

public class LoginInterceptor implements HandlerInterceptor {


    //在拦截前做的事
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if(user==null){
            //拦截
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
