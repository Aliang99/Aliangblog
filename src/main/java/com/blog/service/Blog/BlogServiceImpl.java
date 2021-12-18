package com.blog.service.Blog;

import com.blog.dao.BlogRepository;
import com.blog.handle.NotFoundException;
import com.blog.pojo.Blog;
import com.blog.pojo.QueryBlog;
import com.blog.util.MarkDownUtils;
import com.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author: ALiang
 * @Date: 2021/3/30 2:46
 * @Description:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            //设置创建时间
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //设置浏览次数
            blog.setViews(0);
        }else{
            //设置更新时间
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //查询出id对应的blog
        Blog oldBlog = blogRepository.getOne(id);
        if (oldBlog == null){
            throw new NotFoundException("该博客不存在");
        }
        //将blog的值覆盖查询出来的oldBlog的值
        BeanUtils.copyProperties(blog,oldBlog, MyBeanUtils.getNullPropertyNames(blog));
        //保存oldBlog的值到数据库，此时oldBlog已经是需要更新的值了
        return saveBlog(oldBlog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, QueryBlog blog) {

       return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getType() != null && blog.getType().getId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("type").get("id"),blog.getType().getId()));
                }
                predicates.add(criteriaBuilder.equal(root.get("recommened"),blog.isRecommened()));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return blogRepository.findAll(pageable);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAllByPublished(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlogRecommend(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"views");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTopByUpdateTime(pageable);
    }

    /**
     * 通过传递过来的搜索关键字 查询对应的分页数据
     * @return 分页数据
     */
    @Override
    public Page<Blog> listQueryBlog(String query,Pageable pageable) {

        return  blogRepository.findByQuery(query,pageable);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id){
        blogRepository.updateViews(id);
        Blog blog = blogRepository.getOne(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在");}
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        //获取所有年份
        List<String> years = blogRepository.findGroupYear();
        //使用lamda表达式进行排序操作
        Map<String,List<Blog>> map = new TreeMap<>((k1,k2)->{
            return k2.compareTo(k1);
        });
        //根据年份查找对应的Blog
        for (String year : years) {
            map.put(year,blogRepository.findYear(year));
        }
        //排序操作
        return map;
    }

    @Override
    public Long getCount() {
        return blogRepository.count();
    }

}
