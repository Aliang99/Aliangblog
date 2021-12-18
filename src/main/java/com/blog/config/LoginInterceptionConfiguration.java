package com.blog.config;

import com.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ALiang
 * @Date: 2021/3/27 17:41
 * @Description:
 */
@Configuration
public class LoginInterceptionConfiguration implements WebMvcConfigurer {
    /**
     * 添加拦截器配置
     * @param registry 拦截器的注册对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") //拦截根目录下的admin下的所有请求
                .excludePathPatterns("/admin") //不包括根目录下的admin路径
                .excludePathPatterns("/admin/") //不包括根目录下的admin/路径
                .excludePathPatterns("/admin/login");//不包括根目录下的admin下的login路径
    }
}
