package com.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/25 1:33
 * @Description:类型实体类
 */
@Entity
@Table(name="t_type")
public class Type {
    //实体属性处理
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String typeName;


    //实体关联关系处理
    @OneToMany(mappedBy = "type") //一个Type对应多个Blog ,mappedBy表示通过“type字段进行映射” 由多的一方映射另一方
    private List<Blog> blogs=new ArrayList<>();

    public Type() {
    }

    public Type(Long id, @NotBlank(message = "分类名称不能为空") String typeName, List<Blog> blogs) {
        this.id = id;
        this.typeName = typeName;
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
