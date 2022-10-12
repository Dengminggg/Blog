package com.dm.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 评论表vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论昵称
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;
    //评论者id
    private Long createBy;

    private Date createTime;
    //评论者昵称
    private String username;
    //子评论集合
    private List<CommentVo> children;
}
