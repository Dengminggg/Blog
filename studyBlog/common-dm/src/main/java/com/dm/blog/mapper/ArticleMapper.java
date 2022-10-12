package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
