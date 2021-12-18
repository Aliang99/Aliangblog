package com.blog.controller.Index;

import com.blog.pojo.QueryBlog;
import com.blog.pojo.Tag;
import com.blog.service.Blog.BlogService;
import com.blog.service.Tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IndexTagsController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    /**
     * 返回标签页面
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model) throws Exception {
        List<Tag> tags = tagService.listTagTop6(100); //查询所有的分类对象
        if (id == -1){
            id = tags.get(0).getId(); //将列表中第一个分类的id作为页面首次访问的分类id
            System.out.println("id="+id);
        }

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }


}
