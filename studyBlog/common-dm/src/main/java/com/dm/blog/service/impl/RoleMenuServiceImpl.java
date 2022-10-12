package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.domain.entity.RoleMenu;
import com.dm.blog.mapper.RoleMenuMapper;
import com.dm.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author dm
 * @since 2022-10-11 10:01:58
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

