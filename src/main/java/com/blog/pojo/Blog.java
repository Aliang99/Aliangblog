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
 * @Date: 2021/3/25 1:25
 * @Description:博客实体类
 */
@Entity
@Table(name="t_blog") //绑定数据表
public class Blog {

    //实体属性处理
    @Id //指定一个字段为id
    @GeneratedValue //设置id为自增长
    private Long id;
    private String title; //标题

    @Basic(fetch = FetchType.LAZY) //懒加载
    @Lob //设置为大数据类型，一篇博客的文本数量是很大的
    private String content;//博客内容

    @Basic(fetch = FetchType.LAZY) //懒加载
    @Lob //设置为大数据类型，有时候的配图地址非常的长 可能会超过默认的255
    private String firstPicture;//博客配图
    private String typeName; //原创、转载或翻译
    private Integer views;//浏览数
    private boolean appreciation;//赞赏是否开启
    private boolean shareStatement;//转载申请是否开启
    private boolean commentabled;//是否开启评论
    private boolean published;//是否发布
    private boolean recommened;//是否推荐
    @Basic(fetch = FetchType.LAZY) //懒加载
    @Lob //设置为大数据类型，一篇博客的描述内容可能会超过255
    private String discription;


    private String tagIds;

    //实体属性格式与数据库格式转换处理
    @Temporal(TemporalType.TIMESTAMP) //将java.util.Date所产生的时间格式与数据库对应起来
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP) //将java.util.Date所产生的时间格式与数据库对应起来
    private Date updateTime;//修改时间


    //实体关联关系处理
    @ManyToOne
    private Type type;
    @ManyToMany //多对多映射 任意选一方做关系映射
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Blog(Long id, String title, String content, String firstPicture, String typeName, Integer views, boolean appreciation, boolean shareStatement, boolean commentabled, boolean published, boolean recommened, String discription, String tagIds, Date createTime, Date updateTime, Type type, List<Tag> tags, User user, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.typeName = typeName;
        this.views = views;
        this.appreciation = appreciation;
        this.shareStatement = shareStatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommened = recommened;
        this.discription = discription;
        this.tagIds = tagIds;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.tags = tags;
        this.user = user;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", typeName='" + typeName + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommened=" + recommened +
                ", discription='" + discription + '\'' +
                ", tagIds='" + tagIds + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommened() {
        return recommened;
    }

    public void setRecommened(boolean recommened) {
        this.recommened = recommened;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
