package com.dm.blog.timer;


import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.entity.Article;
import com.dm.blog.service.ArticleService;
import com.dm.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务：更新redis的数据到mysql数据库
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void UpdateViewCountJobTimer() {
        //获取redis中的文章浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.Article_View_Count);
        //利用stream流封装为Article
        List<Article> collect = cacheMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新数据库
        articleService.updateBatchById(collect);
    }

}
