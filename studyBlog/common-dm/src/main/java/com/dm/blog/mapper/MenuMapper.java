package com.dm.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.blog.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author dm
 * @since 2022-10-08 20:33:24
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(@Param("id") Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuByUserId(@Param("userId") Long userId);

}
