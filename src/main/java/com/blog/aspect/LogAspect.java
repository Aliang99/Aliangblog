package com.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.core.ApplicationContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ALiang
 * @Date: 2021/3/24 16:34
 * @Description: 日志切面类 拦截所有的controller类 并进行日志记录
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //拦截返回值为任意值 com.blog.blo.controller下的任何类的 带任意参数的 任意方法
    @Pointcut("execution(* com.blog.controller.*.*(..))")
    public void log(){}

    //在拦截的方法前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){ //JoinPoint切面对象 通过切面对象可以反射获取类的方法名和参数值
       /*在请求指定的controller之前，统计以下参数
       * 请求的URL--->URL
       * 请求者的ip--->IP
       * 请求的方法名--->Method
       * 请求的参数--->args
       * */
       //1、获取到request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //2、获取request中需要获取的参数值
        String URL = request.getRequestURL().toString(); //获取URL
        String ip = request.getRemoteAddr();//获取ip
        //获取请求的方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取此次请求携带的参数
        Object[] args = joinPoint.getArgs();

        //封装到私有内部类 RequestLog中
        RequestLog rl = new RequestLog(URL,ip,classMethod,args);
        System.out.println(rl);

        logger.info("RequestInfo:",rl);

    }

    //在拦截的方法后执行
    @After("log()")
    public void doAfter(){
        System.out.println("--------doAfter---------");
    }

    //方法返回之前执行 将返回值作为参数传递到该方法
    //两个参数 一个是返回值，一个是切入点方法
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        System.out.println("----returnStr:"+result+"------------");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
