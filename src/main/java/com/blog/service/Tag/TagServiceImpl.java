package com.blog.service.Tag;

import com.blog.dao.TagRepository;
import com.blog.handle.NotFoundException;
import com.blog.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:05
 * @Description: 对分类的增删改查业务操作  每一个操作都加了事务操作
 */
@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        //1、根据id查询到tag
        Tag t = tagRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该标签");
        }
        //2、如果存在该id,那么就把传递过来的tag 赋值到查询出来的t
        BeanUtils.copyProperties(tag,t);
        //3、将此时保存了tag的值的对象t,保存到数据库中，完成一次更新操作
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
       tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByTagName(String tagName) {
        return tagRepository.getTagByTagName(tagName);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.getTagById(id);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop6(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop6(pageable);
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i=0;i< idarray.length;i++){
                list.add(Long.valueOf(idarray[i]));
            }
        }
        return list;
    }
}
