package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.RoleDto;
import com.dm.blog.domain.dto.UpdateRoleDto;
import com.dm.blog.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("展示角色列表")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        return roleService.list(pageNum, pageSize, roleDto);
    }

    @ApiOperation("展示所有状态正常的角色列表")
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }

    @ApiOperation("改变角色状态")
    @PutMapping("/changeStatus")
    public ResponseResult updateRoleStatus(@RequestBody RoleDto roleDto) {
        return roleService.updateRoleStatus(roleDto);
    }

    @ApiOperation(" 新增角色")
    @PostMapping
    public ResponseResult saveRole(@RequestBody RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @ApiOperation("角色信息回显")
    @GetMapping("/{roleId}")
    public ResponseResult role(@PathVariable("roleId") Long id) {
        return roleService.role(id);
    }

    @ApiOperation("更新角色信息")
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public ResponseResult deleteRole(@PathVariable(name = "id") Long id) {
        return roleService.deleteRole(id);
    }

}
