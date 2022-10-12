package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.ArticleDto;
import com.dm.blog.domain.entity.Article;
import com.dm.blog.domain.vo.ArticleListVo;
import com.dm.blog.domain.vo.HotArticleVo;
import com.dm.blog.domain.vo.PageVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult<List<HotArticleVo>> hotArticleList();

    ResponseResult<PageVo<ArticleListVo>> articleList(Long categoryId, Integer pageNum, Integer pageSize);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult article(ArticleDto articleDto);

    ResponseResult allArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult getArticle(Integer id);

    ResponseResult updateArticle(ArticleDto articleDto);

    ResponseResult deleteArticle(Integer id);
}
