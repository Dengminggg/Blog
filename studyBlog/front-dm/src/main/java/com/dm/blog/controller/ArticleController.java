package com.dm.blog.controller;

import com.dm.blog.annotation.SystemLog;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.vo.ArticleListVo;
import com.dm.blog.domain.vo.HotArticleVo;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @SystemLog(BusinessName = "热点文章")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @SystemLog(BusinessName = "分类文章")
    @GetMapping("/articleList")
    public ResponseResult<PageVo<ArticleListVo>> articleList(Long categoryId, Integer pageNum, Integer pageSize) {
        return articleService.articleList(categoryId, pageNum, pageSize);
    }

    @SystemLog(BusinessName = "查看文章具体详情")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @SystemLog(BusinessName = "更新文章阅读量")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }
}
