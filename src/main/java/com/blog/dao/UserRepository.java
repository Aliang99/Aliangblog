package com.blog.dao;

import com.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ALiang
 * @Date: 2021/3/26 2:12
 * @Description:
 */
//通过继承JpaRepository<User,Long> UserRepository这个类就具备了增删改查的能力
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 查询数据库中对应的username和password 的用户信息
     * @return
     */
    User findByUsernameAndPassword(String username,String password);

}
