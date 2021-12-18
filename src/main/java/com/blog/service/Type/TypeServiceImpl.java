package com.blog.service.Type;

import com.blog.dao.TypeRepository;
import com.blog.handle.NotFoundException;
import com.blog.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:05
 * @Description: 对分类的增删改查业务操作  每一个操作都加了事务操作
 */
@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getTypeById(Long id) {
        return typeRepository.getTypeById(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //1、根据id查询到type
        Type t = typeRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该分类");
        }
        //2、如果存在该id,那么就把传递过来的type 赋值到查询出来的t
        BeanUtils.copyProperties(type,t);
        //3、将此时保存了type的值的对象t,保存到数据库中，完成一次更新操作
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByTypeName(String typeName) {
        Type type = typeRepository.getTypeByTypeName(typeName);
        return type;
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }


    @Override
    public List<Type> listTypeTop6(Integer size) {
        Sort sort =Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort); //创建一个指定大小的分页对象
        return typeRepository.findTop(pageable);
    }
}
