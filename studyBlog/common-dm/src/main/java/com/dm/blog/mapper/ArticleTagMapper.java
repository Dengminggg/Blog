package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author dm
 * @since 2022-10-10 09:26:09
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
