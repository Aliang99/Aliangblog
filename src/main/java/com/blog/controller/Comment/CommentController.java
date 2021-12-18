package com.blog.controller.Comment;

import com.blog.pojo.Comment;
import com.blog.pojo.User;
import com.blog.service.Blog.BlogService;
import com.blog.service.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Random;

/**
 * @Author: ALiang
 * @Date: 2021/4/28 17:19
 * @Description:
 */
@Controller

public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
   private String avatar; //头像给定静态的

    @GetMapping("/comments/{blogId}")
    public String commnets(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    //响应POST请求
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (!Objects.isNull(user) && comment.getEmail() == user.getEmail() && comment.getNickname()==user.getNickname()){
            //管理员头像
            comment.setAvatar("/images/about.jpg");
            //设置为管理原评论
            comment.setAdmin(true);
        }else{
               Random random = new Random();
               int x = random.nextInt(19) + 1;
               comment.setAvatar("/images/userimg/user-"+x+".jpg");
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;
    }
}
