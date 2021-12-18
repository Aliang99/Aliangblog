package com.blog.handle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: ALiang
 * @Date: 2021/3/24 15:56
 * @Description: 捕获未找到的异常 进行记录
 */

@ResponseStatus(HttpStatus.NOT_FOUND) //状态码为404的请求 其响应体进入该方法处理
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
