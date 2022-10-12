package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.Menu;
import com.dm.blog.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("展示菜单列表")
    @GetMapping("/list")
    public ResponseResult list(String status, String menuName) {
        return menuService.list(status, menuName);
    }

    @ApiOperation("新增菜单")
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @ApiOperation("根据id查询菜单数据")
    @GetMapping("/{menuId}")
    public ResponseResult getMenuById(@PathVariable Long menuId) {
        return menuService.getMenuById(menuId);
    }

    @ApiOperation("更新菜单")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteMenu(menuId);
    }

    @ApiOperation("获取菜单树接口")
    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }

    @ApiOperation("加载对应角色菜单列表树")
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        return menuService.roleMenuTreeSelect(roleId);
    }

}
