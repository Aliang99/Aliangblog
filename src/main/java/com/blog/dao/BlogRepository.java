package com.blog.dao;

import com.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:05
 * @Description:
 */
//JpaSpecificationExecutor接口中 定义了一些对于不确定条件数量 的查询，但要保证条件的名称在泛型Blog中
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b " +
            "from Blog b " +
            "where b.recommened = true and b.published = true")
    List<Blog> findTopByUpdateTime(Pageable pageable);



    @Query("select b " +
            "from Blog b " +
            "where (b.title like ?1 or b.content like ?1) and b.published=true")
    Page<Blog>  findByQuery(String query,Pageable pageable);



    @Query("select function('date_format',b.createTime,'%Y') as year " +
            "from Blog b " +
            "where b.published=true " +
            "group by function('date_format',b.createTime,'%Y') " +
            "order by year asc ")
    List<String> findGroupYear();


    @Query("select b " +
            "from Blog b " +
            "where b.published=true and function('date_format',b.createTime,'%Y') = ?1")
    List<Blog> findYear(String year);



    @Query("select b " +
            "from Blog b " +
            "where b.published = true")
    Page<Blog> findAllByPublished(Pageable pageable);

    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

}

