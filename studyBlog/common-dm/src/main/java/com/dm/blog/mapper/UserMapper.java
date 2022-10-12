package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-03 20:16:44
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
