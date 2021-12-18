package com.blog.controller.Index;

import com.blog.service.Blog.BlogService;
import com.blog.service.Tag.TagService;
import com.blog.service.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: ALiang
 * @Date: 2021/3/24 0:26
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 返回首页页面
     * @param pageable
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({"/","index","/index/"})
    public String index(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model) throws Exception {

        //获取分页的博客信息
        model.addAttribute("page",blogService.listBlog(pageable));
        //获取分类信息取前6个
        model.addAttribute("types",typeService.listTypeTop6(6));
        //获取标签信息取前6个
        model.addAttribute("tags",tagService.listTagTop6(6));
        //最新推荐栏 取前6个
        model.addAttribute("recommendBlogs",blogService.listBlogRecommend(6));
        return "index";
    }


    /**
     * 用于响应页脚部分数据需要更新的请求
     * @param model
     * @return
     */
    @GetMapping("/footer/newblogs")
    public String newBlogList(Model model){
        model.addAttribute("recommendBlogs",blogService.listBlogRecommend(6));
        return "_fragments::newBlogList";
    }
}
