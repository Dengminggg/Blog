package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author dm
 * @since 2022-10-11 12:44:48
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
