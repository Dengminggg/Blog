package com.dm.blog.runner;

import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.entity.Article;
import com.dm.blog.mapper.ArticleMapper;
import com.dm.blog.utils.RedisCache;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 在应用启动时把博客的浏览量存储到redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询全部文章信息  id <==> viewCount
        List<Article> articles = articleMapper.selectList(null);
        //流式进行Map转换
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        //必须从Long强转为Integer，因为redis需要用到自增功能，而Long的存储形式不对
                        article -> article.getViewCount().intValue()));
        //存储到redis中去
        redisCache.setCacheMap(SystemConstants.Article_View_Count, collect);
    }
}
