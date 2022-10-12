package com.dm.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Menu;
import com.dm.blog.domain.entity.RoleMenu;
import com.dm.blog.domain.vo.MenuTreeVo;
import com.dm.blog.domain.vo.MenuVo;
import com.dm.blog.domain.vo.RoleMenuTreeSelectVo;
import com.dm.blog.mapper.MenuMapper;
import com.dm.blog.service.MenuService;
import com.dm.blog.service.RoleMenuService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author dm
 * @since 2022-10-08 20:33:24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        List<String> perms = null;
        //如果为admin超级管理员,返回所有权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(wrapper);
            //取出里面的perm
            perms = list.stream().map(new Function<Menu, String>() {
                @Override
                public String apply(Menu menu) {
                    return menu.getPerms();
                }
            }).collect(Collectors.toList());
        } else {
            perms = menuMapper.selectPermsByUserId(id);
        }

        return perms;
    }

    @Override
    public ResponseResult list(String status, String menuName) {

        //设置过滤条件
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        wrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        wrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);

        List<Menu> menuList = list(wrapper);

        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult add(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long menuId) {
        Menu menu = getById(menuId);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        } else {
            updateById(menu);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        //查询是否有子菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, menuId);
        //如果没有子菜单
        if (count(wrapper) == 0) {
            removeById(menuId);
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(500, "存在子菜单不允许删除");
        }
    }

    @Override
    public ResponseResult treeSelect() {
        //查询出所有权限
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);

        //构造权限树
        List<MenuTreeVo> menuTreeVos = menuList.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .collect(Collectors.toList());
        menuTreeVos = builderMenuTree(menuTreeVos, 0L);


        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long roleId) {
        //查询出所有权限
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);

        //构造权限树
        List<MenuTreeVo> menuTreeVos = menus.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .collect(Collectors.toList());
        menuTreeVos = builderMenuTree(menuTreeVos, 0L);

        //查询出角色所关联的菜单权限id列表
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> RoleMenus = roleMenuService.list(wrapper);
        List<Long> checkedKeys = RoleMenus.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());

        //封装返回
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

    private List<MenuTreeVo> builderMenuTree(List<MenuTreeVo> menuTreeVos, long level) {
        List<MenuTreeVo> menuTreeVoList = menuTreeVos.stream().filter(menuTreeVo -> menuTreeVo.getParentId().equals(level))
                .map(menuTreeVo -> menuTreeVo.setChildren(builderMenuTree(menuTreeVos, menuTreeVo.getId())))
                .collect(Collectors.toList());

        return menuTreeVoList;
    }
}

