package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author dm
 * @since 2022-10-11 10:01:58
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
