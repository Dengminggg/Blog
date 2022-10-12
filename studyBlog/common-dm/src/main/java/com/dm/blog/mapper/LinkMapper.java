package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-29 13:58:10
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
