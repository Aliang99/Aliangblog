package com.blog.controller.Index;

import com.blog.pojo.Blog;
import com.blog.pojo.QueryBlog;
import com.blog.pojo.Type;
import com.blog.service.Blog.BlogService;
import com.blog.service.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/4/16 17:10
 * @Description:
 */
@Controller
public class IndexTypesController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    /**
     * 返回分类页面
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) throws Exception {
        List<Type> types = typeService.listTypeTop6(100); //查询所有的分类对象
        if (id == -1){
            id = types.get(0).getId(); //将列表中第一个分类的id作为页面首次访问的分类id
            System.out.println("id="+id);
        }
        QueryBlog queryBlog = new QueryBlog(null,typeService.getTypeById(id),true);
        model.addAttribute("types",types);
        Page<Blog> blogs = blogService.listBlog(pageable, queryBlog);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTypeId",id);
        return "types";
    }



}
