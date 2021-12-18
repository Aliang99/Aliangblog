package com.blog.controller.Index;

import com.blog.pojo.Blog;
import com.blog.service.Blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ALiang
 * @Date: 2021/4/16 17:10
 * @Description:
 */
@Controller
public class IndexBlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 返回对应博客的详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String getBlog(@PathVariable Long id, Model model){
        Blog andConvert = blogService.getAndConvert(id);
        if(andConvert.isPublished()){ //是否是发布状态
            model.addAttribute("blog",andConvert);
        }else{
            return "index";
        }
        return "blog";
    }
}
