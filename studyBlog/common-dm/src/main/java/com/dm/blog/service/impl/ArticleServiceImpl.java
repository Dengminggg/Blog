package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.domain.dto.ArticleDto;
import com.dm.blog.domain.entity.ArticleTag;
import com.dm.blog.domain.entity.Category;
import com.dm.blog.domain.vo.*;
import com.dm.blog.mapper.ArticleMapper;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Article;
import com.dm.blog.service.ArticleService;
import com.dm.blog.service.ArticleTagService;
import com.dm.blog.service.CategoryService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
        //文章不能为草稿，必须为正式文章
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                //根据浏览量降序排列
                .orderByDesc(Article::getViewCount);
        //只提取前10条数据
        Page<Article> page = new Page<>(1, 10);
        page(page, wrapper);

        //拿出数据
        List<Article> records = page.getRecords();


        //从redis中查询实时文章阅读量进行处理
        records.stream().map(article -> {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.Article_View_Count, article.getId().toString());
            return article.setViewCount(viewCount.longValue());
        }).collect(Collectors.toList());


        //进行vo对象数据转移，普通bean拷贝方法
//        ArrayList<HotArticleVo> hotArticleVos = new ArrayList<>();
//        for (Article article : records) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            hotArticleVos.add(vo);
//        }


        //进行vo对象数据转移，BeanCopyUtils拷贝方法
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        //返回响应封装对象
        return ResponseResult.okResult(hotArticleVos);

    }

    @Override
    public ResponseResult<PageVo<ArticleListVo>> articleList(Long categoryId, Integer pageNum, Integer pageSize) {
        //查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        // 如果有categoryId就要查询时要和传入的相同
        wrapper.eq(!ObjectUtils.isEmpty(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                // 状态是正式发布的
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                // 对isTop进行降序
                .orderByDesc(Article::getIsTop);

        //进行分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<Article> articleList = page.getRecords();

        /*
         * 填充categoryName
         */

//        for (Article article : articleList) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        articleList.stream().map(article ->
                article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())
        ).collect(Collectors.toList());

        //从redis中查询实时文章阅读量进行处理
        articleList.stream().map(article -> {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.Article_View_Count, article.getId().toString());
            return article.setViewCount(viewCount.longValue());
        }).collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        return ResponseResult.okResult(new PageVo<ArticleListVo>(articleListVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查找文章详情
        Article article = getById(id);
        //处理文章类别名
        Category category = categoryService.getById(article.getId());
        if (category != null) {
            article.setCategoryName(category.getName());
        }
        //从redis中查询实时文章阅读量进行处理
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.Article_View_Count, id.toString());
        article.setViewCount(viewCount.longValue());
        //进行vo对象转换
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //返回封装结果
        return ResponseResult.okResult(articleDetailVo);
    }

    //往redis中更新对应id的浏览量
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(SystemConstants.Article_View_Count, id.toString(), 1);
        return ResponseResult.okResult();
    }


    //保证全局事务
    @Transactional
    @Override
    public ResponseResult article(ArticleDto articleDto) {
        //存入博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        Long id = article.getId();
        //进行Article-Tag表数据的提取
        List<ArticleTag> collect = articleDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag(id, tagId)).collect(Collectors.toList());
        //存入博客和标签的关联
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult allArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {

        //进行模糊匹配查询
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        wrapper.like(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());

        //进行分页
        Page<Article> page = new Page<>(pageNum, pageSize);

        page(page, wrapper);
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleVo.class);

        PageVo<ArticleVo> pageVo = new PageVo<>(articleVos, page.getTotal());

        //封装返回
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticle(Integer id) {
        //根据id查找文章详情
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> list = articleTagService.list(wrapper);
        List<Long> tagIds = list.stream().map(ArticleTag::getTagId).collect(Collectors.toList());

        //封装对象
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(tagIds);

        return ResponseResult.okResult(articleVo);

    }

    @Override
    public ResponseResult updateArticle(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
        //删除原有的标签和博客的关联
        LambdaUpdateWrapper<ArticleTag> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(wrapper);

        //添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteArticle(Integer id) {
        //根据文章id在数据库删除文章
        removeById(id);

        return ResponseResult.okResult();
    }

}
