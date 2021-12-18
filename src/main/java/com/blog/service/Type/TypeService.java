package com.blog.service.Type;

import com.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:01
 * @Description:
 */
public interface TypeService {
    /**
     * 新增一个分类
     * @param type 分类对象
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id获取一个类型
     * @param id 分类id
     * @return
     */
    Type getTypeById(Long id);

    /**
     * 获取全部分类
     * @param pageable spring提供的分页数据存放对象
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 根据id，更新分类数据
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**
     * 根据id删除分类
     * @param id
     */
    void deleteType(Long id);

    /**
     * 更具分类名称获取分类对象
     * @param typeName
     * @return
     */
    Type getTypeByTypeName(String typeName);

    List<Type> listType();

    List<Type> listTypeTop6(Integer size);

}
