package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.RoleDto;
import com.dm.blog.domain.dto.UpdateRoleDto;
import com.dm.blog.domain.entity.Role;
import com.dm.blog.domain.entity.RoleMenu;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.domain.vo.RoleVo;
import com.dm.blog.mapper.RoleMapper;
import com.dm.blog.service.RoleMenuService;
import com.dm.blog.service.RoleService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author dm
 * @since 2022-10-08 20:33:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRolesByUserId(Long id) {
        List<String> roles = new ArrayList<>();
        //判断是否是admin超级管理员 如果是返回集合中只需要有admin
        if (SecurityUtils.isAdmin()) {
            roles.add("admin");
        } else {
            roles = roleMapper.selectRoleKeyByUserId(id);
        }

        return roles;
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        //添加过滤条件
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(roleDto.getRoleName()), Role::getRoleName, roleDto.getRoleName());
        wrapper.eq(StringUtils.hasText(roleDto.getStatus()), Role::getStatus, roleDto.getStatus());
        wrapper.orderByAsc(Role::getRoleSort);

        //进行分页显示
        Page<Role> rolePage = new Page<>(pageNum, pageSize);
        page(rolePage, wrapper);

        //封装返回
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(rolePage.getRecords(), RoleVo.class);
        PageVo<RoleVo> rolePageVo = new PageVo<>(roleVos, rolePage.getTotal());
        return ResponseResult.okResult(rolePageVo);
    }

    @Override
    public ResponseResult updateRoleStatus(RoleDto roleDto) {
        //转换对象
        Role role = new Role();
        role.setId(roleDto.getRoleId());
        role.setStatus(roleDto.getStatus());

        //进行更新操作
        updateById(role);

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult saveRole(RoleDto roleDto) {
        //添加到role表
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        //role自动生成id
        save(role);

        //添加role-menu表
        Long[] menuIds = roleDto.getMenuIds();
        if (!ObjectUtils.isEmpty(menuIds)) {
            List<RoleMenu> roleMenus = Arrays.stream(menuIds)
                    .map(menuId -> new RoleMenu(role.getId(), menuId))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenus);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult role(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        //更新role字段
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);

        //更新role-menu字段->先删除原有menu再添加
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, updateRoleDto.getId());
        roleMenuService.remove(wrapper);

        List<RoleMenu> roleMenus = Arrays.stream(updateRoleDto.getMenuIds())
                .map(menuId -> new RoleMenu(updateRoleDto.getId(), menuId))
                .collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        //查询状态正常的角色
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(wrapper);

        //封装返回
        return ResponseResult.okResult(roles);
    }
}

