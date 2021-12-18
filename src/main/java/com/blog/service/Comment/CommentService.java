package com.blog.service.Comment;

import com.blog.pojo.Comment;

import java.util.List;

/**
 * @Author: ALiang
 * @Date: 2021/4/28 17:26
 * @Description:
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
