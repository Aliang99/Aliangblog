package com.blog.service.Blog;

import com.blog.pojo.Blog;
import com.blog.pojo.QueryBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: ALiang
 * @Date: 2021/3/30 2:39
 * @Description:
 */
@Service
public interface BlogService {

    /**
     * 添加（保存一个blog）
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 通过id获取一个Blog
     * @param id
     * @return
     */
    Blog getBlog(Long id);


    /**
     *
     * 通过id查询一个blog,再把传过来的blog的值覆盖掉查出来的blog
     * @param id
     * @param blog
     * @return
     */
    Blog updateBlog(Long id,Blog blog);

    /**
     * 根据id 删除一个blog
     * @param id
     */
    void deleteBlog(Long id);

    /**
     * 根据blog中存放的条件 查询出符合条件的blog数据 并进行分页操作，blogs_manager查询表单
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, QueryBlog blog);

    //blogs_manager表格
    Page<Blog> listBlog(Pageable pageable, Blog blog);

    //获取全部分页博客信息，首页使用
    Page<Blog> listBlog(Pageable pageable);

    //根据标签id查询博客的分页数据
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //最新推荐功能 首页的最新推荐模块
    List<Blog> listBlogRecommend(Integer size);

    //搜索功能 导航栏的搜索功能
    Page<Blog> listQueryBlog(String query,Pageable pageable);

    //博客markdown格式转换html 博客详情的格式转换
    Blog getAndConvert(Long id);

    //归档
    Map<String,List<Blog>> archivesBlog();

    Long getCount();
}
