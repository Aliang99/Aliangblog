package com.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: ALiang
 * @Date: 2021/3/31 21:28
 * @Description:
 */
public class QueryBlog {


    private String title;
    private Type type;
    private boolean recommened;

    public QueryBlog() {
    }

    public QueryBlog(String title, Type type, boolean recommened) {
        this.title = title;
        this.type = type;
        this.recommened = recommened;
    }

    public QueryBlog(Type type) {
        this.type = type;
    }

    public QueryBlog(String title, Type typeById, Boolean aBoolean) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRecommened() {
        return recommened;
    }

    public void setRecommened(boolean recommened) {
        this.recommened = recommened;
    }

    @Override
    public String toString() {
        return "QueryBlog{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", recommened=" + recommened +
                '}';
    }
}
