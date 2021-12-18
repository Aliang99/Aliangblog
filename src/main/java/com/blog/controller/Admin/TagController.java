package com.blog.controller.Admin;

import com.blog.pojo.Tag;
import com.blog.service.Tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:17
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    /**
     * 返回标签管理页面，并展示分页结果
     * @param pageable springboot自动封装的pageable对象
     * @return
     * @Discreption @PageableDefault(size = 15,sort = {"id"},direction = Sort.DEFAULT_DIRECTION.DESC)
     *              是springboot提供的分页注解，可以指定分页的大小，一页15条，默认一页10条
     *              sort表示分页数据的排序操作，设置排序字段为id，direction表示排序的方向，设置为按id升序排序
     */
    @GetMapping("/tags_manager")
    public String tagManager(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model){ //springboot提供的Pageable会对前端发送过来的数据自动封装成一个pageable对象
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags_manager";
    }

    /**
     * 返回新增标签页面
     * @param model
     * @return
     */
    @GetMapping("/tags_add")
    public String tagsAdd(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags_add";
    }

    /**
     * 响应添加标签的POST请求，并返回到标签管理页面，提示是否新增成功
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String tags(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //业务层校验 重复的类型不允许添加 先判断数据库中是否存在该标签名
        Tag tag1 = tagService.getTagByTagName(tag.getTagName());
        if(tag1 != null){
            result.rejectValue("tagName","tagNameError","\""+tag.getTagName()+"\"标签已存在");
        }
        if (result.hasErrors()){
            return "admin/tags_add";
        }

        Tag t = tagService.saveTag(tag);
        if(t == null){
            //没保存成功 返回提示信息
            attributes.addFlashAttribute("msg","\""+tag.getTagName()+"\"标签失败");
        }else{
            //保存成功，重定向到标签管理页面
            attributes.addFlashAttribute("msg","\""+tag.getTagName()+"\"添加标签成功");
        }
        return "redirect:/admin/tags_manager";
    }

    /**
     * 响应修改标签的POST请求，并返回到标签管理页面，提示是否修改成功
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/tags/update/{id}")
    public String tagsUpdateById(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes,Model model){

        Tag t1 = tagService.getTagById(id);
        if (t1 == null ){
            model.addAttribute("msg","编号有误，请检查地址栏是否正确");
            model.addAttribute("tag",tag);
            return "admin/tags_add";
        }
        //业务层校验 重复的类型不允许添加 先判断数据库中是否存在该标签名
        Tag tag1 = tagService.getTagByTagName(tag.getTagName());
        if(tag1 != null){
            result.rejectValue("tagName","tagNameError","\""+tag.getTagName()+"\"标签已存在");
        }
        //校验传递的参数格式是否符合条件，不符合，直接驳回请求
        if (result.hasErrors()){
            return "admin/tags_add";
        }
        String tagName = tagService.getTag(id).getTagName();
        //校验没问题之后进行数据库更新操作
        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            //没保存成功 返回提示信息
            attributes.addFlashAttribute("msg","对 \""+tagName+"\" 标签的删除操作失败");
        }else{
            //保存成功，重定向到标签管理页面
            attributes.addFlashAttribute("msg","对 \""+tagName+"\" 标签的删除操作成功，修改后的值为: \""+t.getTagName()+"\" ");
        }
        return "redirect:/admin/tags_manager";
    }

    /**
     * 根据id，删除对应的Tag数据
     * @return
     */
    @GetMapping("tags_delete/{id}")
    public String tagsDelete(@PathVariable Long id, RedirectAttributes attributes){
        /**
         * 删除操作的逻辑：
         *  0、校验传递的参数格式是否符合条件，不符合，直接驳回请求
         *  1、从前端传递id值
         *  2、根据id值获取tagName
         *  3、如果tagName不为空，则进行删除
         *  4、如果tagName为空，返回提示，删除失败，未找到该标签
         *  5、验证删除是否成功
         *  6、查询传递过来的id对应的tag
         *  7、判断tag中是否有数据
         *  8、tag中没有数据，表示删除成功，数据库中未查找到
         *  9、tag中有数据，表示删除失败，返回提示信息
         *  10、重定向到tags_manager页面
         */

        //根据id值获取tagName
        String tagName=tagService.getTag(id).getTagName();
        if (tagName!=null){
            //如果tagName不为空，则进行删除
            //调用service删除tag
            tagService.deleteTag(id);
        }else{
            //如果tagName为空，返回提示，删除失败，未找到该标签
            attributes.addFlashAttribute("msg","对 \""+tagName+"\" 标签的删除操作失败");
        }

        //验证删除是否成功
        //通过id查询tag
        Tag t = tagService.getTagByTagName(tagName);
        //如果查询结果为空
        if ((t == null) || (t.getId()==null) || (t.getTagName()=="")){
            //tag中没有数据，表示删除成功，数据库中未查找到
            attributes.addFlashAttribute("msg","对 \""+tagName+"\" 标签的删除操作成功");
        }else{
            //tag中有数据，表示删除失败，返回提示信息
            attributes.addFlashAttribute("msg","对 \""+tagName+"\" 标签的删除操作失败");
        }
        return "redirect:/admin/tags_manager";
    }

    /**
     * 查询标签信息，并返回标签更新页面进行展示，处理用户在地址栏输入不存在的标签id问题
     * @param id
     * @param model
     * @return
     */
    @GetMapping("tags_update/{id}")
    public String tagsUpdate(@PathVariable Long id,Model model){
        if (id == null){
            return "admin/tags_manager";
        }
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags_add";
    }

}
