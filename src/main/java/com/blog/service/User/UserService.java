package com.blog.service.User;

import com.blog.pojo.User;

/**
 * @Author: ALiang
 * @Date: 2021/3/26 2:10
 * @Description:
 */
public interface UserService {
    /**
     * 检查用户名和密码 并返回用户对象
     * @param username 用户名
     * @param password 密码
     * @return 用户名对应的用户对象
     */
    User CheckUserNameAndPassword(String username, String password);
}
