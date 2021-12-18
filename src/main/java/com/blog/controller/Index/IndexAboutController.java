package com.blog.controller.Index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: ALiang
 * @Date: 2021/4/16 17:11
 * @Description:
 */
@Controller
public class IndexAboutController {
    /**
     * 返回about页面
     * @return
     * @throws Exception
     */
    @GetMapping("about")
    public String about() throws Exception {
        return "about";
    }

}
