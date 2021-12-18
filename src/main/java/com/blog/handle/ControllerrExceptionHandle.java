package com.blog.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ALiang
 * @Date: 2021/3/24 15:03
 * @Description: 拦截所有controller的异常
 */

@ControllerAdvice
public class ControllerrExceptionHandle{
    //记录日志的logger对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) //@ExceptionHandle表示拦截的异常，并传入异常的类，这个类下的子类也会被拦截
    public ModelAndView exceptionHandle(HttpServletRequest request, Exception e) throws Exception {
        //记录异常的URL以及异常信息
        logger.error("Request URL:{}, Exception:{}",request.getRequestURL(),e);

        //对手动标有状态码的 且发生了异常的请求直接抛出异常 不跳转到error页面
        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class) != null){
            //抛出异常后，会被ControllerExceptionHandle 类捕获到
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        //将异常信息保存到域中 传到前端页面
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //设置异常的提示页面
        mv.setViewName("error/error");
        return mv;
    }
}
