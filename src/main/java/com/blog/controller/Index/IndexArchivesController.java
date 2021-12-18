package com.blog.controller.Index;

import com.blog.service.Blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: ALiang
 * @Date: 2021/4/13 19:05
 * @Description:
 */

@Controller
public class IndexArchivesController {

    @Autowired
    private BlogService blogService;

    /**
     * 返回归档页面
     * @param model
     * @return
     */
    @GetMapping("archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",blogService.archivesBlog());
        model.addAttribute("blogCount",blogService.getCount());
        return "archives";
    }
}
