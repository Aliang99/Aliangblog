package com.blog.service.Tag;

import com.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:01
 * @Description:
 */
public interface TagService {
    /**
     * 新增一个标签
     * @param tag 标签对象
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 根据id获取一个标签
     * @param id 标签id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 获取全部
     * @param pageable spring提供的分页数据存放对象
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 根据id，更新标签数据
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 根据id删除标签
     * @param id
     */
    void deleteTag(Long id);

    Tag getTagByTagName(String tagName);

    Tag getTagById(Long id);


    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop6(Integer size);

}
