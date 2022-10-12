package com.dm.blog.service.impl;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Menu;
import com.dm.blog.domain.vo.MenuVo;
import com.dm.blog.domain.vo.RoutersVo;
import com.dm.blog.mapper.MenuMapper;
import com.dm.blog.service.GetRoutersService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetRoutersServiceImpl implements GetRoutersService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseResult getRouters() {

        List<Menu> menus = null;

        //如果admin超级管理员则获取所有符合要求的Menu
        if (SecurityUtils.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //查询当前用户id
            Long userId = SecurityUtils.getUserId();

            //获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }

        //进行bean拷贝转移
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);

        //进行menuTree转换
        List<MenuVo> menuTree = builderMenuTree(menuVos, 0L);

        //返回封装对象
        return ResponseResult.okResult(new RoutersVo(menuTree));
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, long level) {
        List<MenuVo> MenuTree = menuVos.stream().filter(menuVo -> menuVo.getParentId().equals(level))
                .map(menuVo -> menuVo.setChildren(builderMenuTree(menuVos, menuVo.getId()))).collect(Collectors.toList());

        return MenuTree;
    }
}
