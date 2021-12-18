package com.blog.interceptor;

import com.blog.pojo.User;

import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: ALiang
 * @Date: 2021/3/27 16:57
 * @Description: 拦截未登录操作
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect("/interception/login");
            return false;
        }
        return true;
    }
}
