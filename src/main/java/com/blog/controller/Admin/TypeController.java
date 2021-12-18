package com.blog.controller.Admin;

import com.blog.pojo.Type;
import com.blog.service.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

/**
 * @Author: ALiang
 * @Date: 2021/3/28 15:17
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 返回分类管理页面，并展示分页结果
     * @param pageable springboot自动封装的pageable对象
     * @return
     * @Discreption @PageableDefault(size = 15,sort = {"id"},direction = Sort.DEFAULT_DIRECTION.DESC)
     *              是springboot提供的分页注解，可以指定分页的大小，一页15条，默认一页10条
     *              sort表示分页数据的排序操作，设置排序字段为id，direction表示排序的方向，设置为按id升序排序
     */
    @GetMapping("/types_manager")
    public String typeManager(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model){ //springboot提供的Pageable会对前端发送过来的数据自动封装成一个pageable对象
        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types_manager";
    }

    /**
     * 返回添加类型的页面
     * @return
     */
    @GetMapping("/types_add")
    public String typesAdd(Model model){
        //前端需要获取一个type，所以返回一个空的也行
        model.addAttribute("type",new Type());
        return "admin/types_add";
    }


    /**
     * 对添加分类POST请求的处理
     * @param type
     * @return 返回到分类管理页面
     */
    @PostMapping("/types")
    public String types(@Valid  Type type, BindingResult result, RedirectAttributes attributes){
        //业务层校验 重复的类型不允许添加 先判断数据库中是否存在该分类名
        Type type1 = typeService.getTypeByTypeName(type.getTypeName());
        if(type1 != null){
            result.rejectValue("typeName","typeNameError","\""+type.getTypeName()+"\"分类已存在");
        }
        //如果传过来的数据进行校验后 不符合校验规则（数据不能为空） 则返回到原页面
        if(result.hasErrors()){
            return "admin/types_add";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            //没保存成功 返回提示信息
            attributes.addFlashAttribute("msg","添加分类\""+type.getTypeName()+"\"失败");
        }else{
            //保存成功，重定向到分类管理页面
            attributes.addFlashAttribute("msg","添加分类\""+type.getTypeName()+"\"成功");
        }
        return "redirect:/admin/types_manager";
    }

    /**
     * 根据id，删除对应的Type数据
     * @return
     */
    @GetMapping("types_delete/{id}")
    public String typesDelete(@PathVariable Long id, RedirectAttributes attributes){
        /**
         * 删除操作的逻辑：
         *  0、校验传递的参数格式是否符合条件，不符合，直接驳回请求
         *  1、从前端传递id值
         *  2、根据id值获取typeName
         *  3、如果typeName不为空，则进行删除
         *  4、如果typeName为空，返回提示，删除失败，未找到该分类
         *  5、验证删除是否成功
         *  6、查询传递过来的id对应的type
         *  7、判断type中是否有数据
         *  8、type中没有数据，表示删除成功，数据库中未查找到
         *  9、type中有数据，表示删除失败，返回提示信息
         *  10、重定向到types_manager页面
         */

        //根据id值获取typeName
        String typeName=typeService.getTypeById(id).getTypeName();
        if (typeName!=null){
            //如果typeName不为空，则进行删除
            //调用service删除type
            typeService.deleteType(id);
        }else{
            //如果typeName为空，返回提示，删除失败，未找到该分类
            attributes.addFlashAttribute("msg","对 \""+typeName+"\" 分类的删除操作失败");
        }

        //验证删除是否成功
        //通过id查询type
        Type t = typeService.getTypeByTypeName(typeName);
        //如果查询结果为空
        if ((t == null) || (t.getId()==null) || (t.getTypeName()=="")){
            //type中没有数据，表示删除成功，数据库中未查找到
            attributes.addFlashAttribute("msg","对 \""+typeName+"\" 分类的删除操作成功");
        }else{
            //type中有数据，表示删除失败，返回提示信息
            attributes.addFlashAttribute("msg","对 \""+typeName+"\" 分类的删除操作失败");
        }
        return "redirect:/admin/types_manager";
    }

    /**
     * 根据id，更新对应的Type数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("types_update/{id}")
    public String typesUpdate(@PathVariable Long id,Model model){
        Type type = typeService.getTypeById(id);
        if (type == null){
            model.addAttribute("msg","输入有误，请检查地址栏中的信息是否有误！");
        }
        model.addAttribute("type",typeService.getTypeById(id));

        return "admin/types_add";
    }


    /**
     * 处理分类更新的POST请求，处理之后返回分类管理页面，并给出提示
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/types/update/{id}")
    public String typesUpdateById(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes,Model model){
        Type t1 = typeService.getTypeById(id);
        if (t1 == null ){
            model.addAttribute("msg","编号有误，请检查地址栏是否正确");
            model.addAttribute("type",type);
            return "admin/types_add";
        }

        //业务层校验 重复的类型不允许添加 先判断数据库中是否存在该分类名
        Type type1 = typeService.getTypeByTypeName(type.getTypeName());
        if(type1 != null){
            result.rejectValue("typeName","typeNameError","\""+type.getTypeName()+"\"分类已存在");
        }
        //校验传递的参数格式是否符合条件，不符合，直接驳回请求
        if (result.hasErrors()){
            return "admin/types_add";
        }
        String typeName = typeService.getTypeById(id).getTypeName();
        //校验没问题之后进行数据库更新操作
        Type t = typeService.updateType(id, type);
        if(t == null){
            //没保存成功 返回提示信息
            attributes.addFlashAttribute("msg","对 \""+typeName+"\" 分类的删除操作失败");
        }else{
            //保存成功，重定向到分类管理页面
            attributes.addFlashAttribute("msg","对 \""+typeName+"\" 标签的删除操作成功，修改后的值为: \""+t.getTypeName()+"\" ");
        }
        return "redirect:/admin/types_manager";
    }

}
