package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.RoleDto;
import com.dm.blog.domain.dto.UpdateRoleDto;
import com.dm.blog.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author dm
 * @since 2022-10-08 20:33:41
 */
public interface RoleService extends IService<Role> {

    List<String> selectRolesByUserId(Long id);

    ResponseResult list(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult updateRoleStatus(RoleDto roleDto);

    ResponseResult saveRole(RoleDto roleDto);

    ResponseResult role(Long id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}
