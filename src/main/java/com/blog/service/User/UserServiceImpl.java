package com.blog.service.User;

import com.blog.dao.UserRepository;
import com.blog.pojo.User;
import com.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ALiang
 * @Date: 2021/3/26 2:11
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User CheckUserNameAndPassword(String username, String password) {
        //将密码进行加密之后 进行传递参数
        System.out.println(MD5Utils.code("111111"));
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
