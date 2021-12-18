package com.blog.controller.Admin;

import com.blog.pojo.User;
import com.blog.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: ALiang
 * @Date: 2021/3/26 0:56
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    /**
     * 登录页面的入口
     * @return
     */
    @GetMapping
    public String LoginPage(){
        return "admin/login_index";
    }

    /**
     * 处理登录表单的请求
     * @param username  表单的用户名
     * @param password  表单的密码
     * @param session   整个session会话对象
     * @param attributes    重定向需求中用来保存数据的域对象
     * @return
     */
    @PostMapping("/login")
    public String loginForm(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        User user = userService.CheckUserNameAndPassword(username, password);
        //用户名或密码不正确
        if(user == null){
            //重定向时 使用RedirectAttributes类中的addFlashAttribute保存数据，数据才不会丢失
            attributes.addFlashAttribute("msg","用户名或密码错误！");
            attributes.addFlashAttribute("userName",username);
            //重定向到登录页面的controller
            return "redirect:/admin";
        }else{
            //用户名或密码正确
            //设置用户对象中的密码为空 不能传到前端去
            user.setPassword("");
            //在session中，存入user对象，方便在前端使用数据
            session.setAttribute("user",user);
            //跳转到登录成功页面
            return "admin/login_success";
        }
    }

    /**
     * 退出账号
     * @param session 整个会话的对象
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session,RedirectAttributes attributes){
        //推出账号之后，必需要将session中保存的user信息移除
        session.removeAttribute("user");
        attributes.addFlashAttribute("msg","账号已退出");
        return "redirect:/admin";
    }

    /**
     * 登录成功页面
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model){
        return "admin/login_success";
    }

}
