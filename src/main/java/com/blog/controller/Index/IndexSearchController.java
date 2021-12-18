package com.blog.controller.Index;

import com.blog.service.Blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: ALiang
 * @Date: 2021/4/16 17:13
 * @Description:
 */
@Controller
public class IndexSearchController {

    @Autowired
    private BlogService blogService;

    /**
     * 处理搜索的POST请求，并转发到search页面
     * @param query
     * @param pageable
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("search")
    public String search(@RequestParam String query, @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model) throws Exception {

        model.addAttribute("page",blogService.listQueryBlog("%"+query+"%", pageable));
        model.addAttribute("query",query);
        return "search";
    }
}
