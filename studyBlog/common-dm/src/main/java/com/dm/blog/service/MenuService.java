package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author dm
 * @since 2022-10-08 20:33:24
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    ResponseResult list(String status, String menuName);

    ResponseResult add(Menu menu);

    ResponseResult getMenuById(Long menuId);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeSelect(Long roleId);
}
