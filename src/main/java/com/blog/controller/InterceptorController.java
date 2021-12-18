package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author: ALiang
 * @Date: 2021/3/27 19:11
 * @Description:
 */
@Controller
@RequestMapping("/interception")
public class InterceptorController {

    /**
     * @param attributes
     * @return
     */
    @GetMapping("/login")
    public String login(RedirectAttributes attributes){
        attributes.addFlashAttribute("msg","对不起，您的操作需要先进行登录");
        return "redirect:/admin";
    }
}
