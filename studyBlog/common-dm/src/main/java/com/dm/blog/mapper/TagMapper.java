package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-08 14:10:13
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
