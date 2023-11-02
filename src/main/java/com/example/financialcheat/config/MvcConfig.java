package com.example.financialcheat.config;

import com.example.financialcheat.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//写好了拦截器之后还不行，还需要在config里配置进来
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {//里面的参数是拦截器的注册器
        //在注册器里面注册我们写的LoginInterceptor拦截器类
        registry.addInterceptor(new LoginInterceptor())
                //排除不需要拦截的路径
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/doc.html"
                ).order(0);
    }

}
