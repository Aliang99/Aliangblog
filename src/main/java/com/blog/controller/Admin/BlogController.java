package com.blog.controller.Admin;

import com.blog.pojo.Blog;
import com.blog.pojo.QueryBlog;
import com.blog.pojo.User;
import com.blog.service.Blog.BlogService;
import com.blog.service.Tag.TagService;
import com.blog.service.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:17
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 请求返回博客添加页面
     * @param model
     * @return
     */
    @Transactional
    @GetMapping("/blogs_add")
    public String blogPut(Model model){

        //为了把类型下拉框的数据渲染出来，所以需要获取全部的type tag并返回到添加页面中供选择
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return "admin/blog_add";
    }

    /**
     * 请求返回博客管理页面 并显示数据库中的博客数据
     *      * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @Transactional
    @GetMapping("/blogs_manager")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model){

        //为了把类型下拉框的数据渲染出来，所以需要获取全部的types并返回到管理页面
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blog_manager";
    }

    /**
     * 按搜索条件查询数据库 并返回对应的查询结果
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @Transactional
    @PostMapping("/blogs_search")
    public String blogsSearch(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, QueryBlog blog, Model model, HttpServletRequest request){

        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blog_manager :: blogList";
    }

    /**
     * 响应新增或者修改博客的POST请求 返回到博客管理页面 并提示是否新增/修改成功
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @Transactional
    @PostMapping("/blogs")
    public String addBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        if (blog.getFirstPicture() == null || blog.getFirstPicture() == ""){
            blog.setFirstPicture("https://picsum.photos/800/500");
        }
        if (blog.getId()!=null){
            //为更新操作的情况
            Blog blog1 = blogService.updateBlog(blog.getId(), blog);
            if (blog1 == null){
                attributes.addFlashAttribute("msg","博客\""+blog1.getTitle()+"\"修改失败");

            }else{
                attributes.addFlashAttribute("msg","博客\""+blog1.getTitle()+"\"修改成功");
            }
        }else{
            //为新增操作的情况
            Blog b = blogService.saveBlog(blog);
            //如果前端点击的是保存，并且操作数据库失败
            if (!blog.isPublished() && b == null){
                attributes.addFlashAttribute("msg","发布失败");
            }else{
                //如果前端点击的是保存，并且操作数据库成功
                if(!blog.isPublished() && b!=null){
                    attributes.addFlashAttribute("msg","发布成功");
                }
            }
            //如果前端点击的是保存，并且操作数据库失败
            if (blog.isPublished() && b == null){
                attributes.addFlashAttribute("msg","保存失败");
            }else{
                //如果前端点击的是保存，并且操作数据库成功
                if(blog.isPublished() && b!=null) {
                    attributes.addFlashAttribute("msg", "保存成功");
                }
            }
        }
        return "redirect:/admin/blogs_manager";
    }


    /**
     * 获取id对应得博客信息，并将查询的博客信息返回到更新页面填充
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs_update/{id}")
    public String updateBlog(@PathVariable Long id,Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",blogService.getBlog(id));
        return "admin/blog_update";
    }

    /**
     * 根据请求的id 删除对应的博客信息
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs_delete/{id}")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
        Blog b = blogService.getBlog(id);
        String blogTitle = b.getTitle();
        if (b != null){
            blogService.deleteBlog(id);
        }else{
            attributes.addFlashAttribute("msg","删除失败，编号有误！");
        }
        attributes.addFlashAttribute("msg","博客\""+blogTitle+"\"删除成功！");
        return "redirect:/admin/blogs_manager";
    }



}
