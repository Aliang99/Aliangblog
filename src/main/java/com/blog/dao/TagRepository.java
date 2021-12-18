package com.blog.dao;

import com.blog.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:05
 * @Description:
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag getTagByTagName(String tagName);



    Tag getTagById(Long id);



    @Query("select t " +
            "from Tag t")
    List<Tag> findTop6(Pageable pageable);
}
