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
 * @Description:标签实体类
 */
@Entity
@Table(name="t_tag")
public class Tag {
    //实体属性处理
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String tagName;


    //实体关联关系处理
    @ManyToMany(mappedBy = "tags") //多对多映射
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName +
                '}';
    }

    public Tag(Long id, @NotBlank(message = "标签名称不能为空") String tagName, List<Blog> blogs) {
        this.id = id;
        this.tagName = tagName;
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
