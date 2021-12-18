package com.blog.dao;

import com.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:05
 * @Description:
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    /**
     * 根据分类名称查询数据库中对应的Type对象并返回
     * @param typeName
     * @return
     */
    Type getTypeByTypeName(String typeName);



    Type getTypeById(Long id);




    @Query("select t " +
            "from Type t") //自定义查询语句
    List<Type> findTop(Pageable pageable);
}
