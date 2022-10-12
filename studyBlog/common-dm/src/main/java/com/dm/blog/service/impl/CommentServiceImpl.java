package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Comment;
import com.dm.blog.domain.vo.CommentVo;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.exception.SystemException;
import com.dm.blog.mapper.CommentMapper;
import com.dm.blog.service.CommentService;
import com.dm.blog.service.UserService;
import com.dm.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-05 15:05:00
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //判断评论类型
        wrapper.eq(Comment::getType, commentType);
        //如果为文章评论接口引入则判断对应的articleId
        wrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //判断根评论即rootId为-1
        wrapper.eq(Comment::getRootId, SystemConstants.ROOT_ID);
        //按时间排序
        wrapper.orderByDesc(Comment::getCreateTime);

        //分页查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, wrapper);

        //封装根评论
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //进一步查询根评论下的子评论集合
        for (CommentVo commentVo : commentVoList) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //后端检验评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        //进行数据库保存
        save(comment);
        //返回成功响应体
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id查询子评论集合
     *
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        //定义查询条件
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, id);
        //按时间排序
        wrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(wrapper);

        //利用toCommentVoList进行封装
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    /**
     * comment到commentVo的属性封装
     *
     * @param records
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> records) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(records, CommentVo.class);
        //遍历commentVos集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询用户昵称并赋值
            String createByNickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(createByNickName);
            //假如toCommentUserId字段不为-1进行查询被评论的用户昵称赋值
            if (commentVo.getToCommentUserId() != -1) {
                String ToCommentUserNickName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(ToCommentUserNickName);
            }
        }

        return commentVos;
    }

}

