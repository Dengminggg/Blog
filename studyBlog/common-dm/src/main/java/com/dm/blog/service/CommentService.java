package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-05 15:05:00
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
