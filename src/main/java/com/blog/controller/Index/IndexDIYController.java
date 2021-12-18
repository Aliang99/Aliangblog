package com.blog.controller.Index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: ALiang
 * @Date: 2021/4/16 23:37
 * @Description:
 */
@Controller
public class IndexDIYController {
    @GetMapping("/diy")
    public String diy(){
        return "diy";
    }
}
