package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.ArticleDto;
import com.dm.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @ApiOperation("新增博文")
    @PostMapping
    public ResponseResult article(@RequestBody ArticleDto articleDto) {
        return articleService.article(articleDto);
    }

    @ApiOperation("提供文章列表")
    @GetMapping("/list")
    public ResponseResult allArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
        return articleService.allArticleList(pageNum, pageSize, articleDto);
    }

    @ApiOperation("查询文章详情")
    @GetMapping(value = "/{id}")
    public ResponseResult getArticle(@PathVariable("id") Integer id) {
        return articleService.getArticle(id);
    }

    @ApiOperation("更新文章")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDto articleDto) {
        return articleService.updateArticle(articleDto);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Integer id) {
        return articleService.deleteArticle(id);
    }
}