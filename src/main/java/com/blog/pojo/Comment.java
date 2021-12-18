package com.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/3/25 1:36
 * @Description:
 */
@Entity
@Table(name="t_comment")
public class Comment {
    //实体属性处理
    @Id
    @GeneratedValue
    private Long id;
    private String nickname; //昵称
    private String email;//邮箱
    private String content;//评论内容
    private String avatar;//头像

    private boolean isAdmin;//管理员评论标识

    //实体属性格式与数据库格式转换处理
    @Temporal(TemporalType.TIMESTAMP) //将java.util.Date所产生的时间格式与数据库对应起来
    private Date createTime;


    //实体关系处理
    @ManyToOne
    private Blog blog;


    //实体自关联处理
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>(); //一条评论包含多个回复
    @ManyToOne
    private Comment parentComment;//一个回复只有一个父评论

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isAdmin=" + isAdmin +
                ", createTime=" + createTime +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Comment(Long id, String nickname, String email, String content, String avatar, boolean isAdmin, Date createTime, Blog blog, List<Comment> replyComments, Comment parentComment) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.avatar = avatar;
        this.isAdmin = isAdmin;
        this.createTime = createTime;
        this.blog = blog;
        this.replyComments = replyComments;
        this.parentComment = parentComment;
    }
}
