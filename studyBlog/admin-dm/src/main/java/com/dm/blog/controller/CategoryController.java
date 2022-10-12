package com.dm.blog.controller;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.CategoryDto;
import com.dm.blog.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询所有分类")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @ApiOperation("分类excel导出")
    @PreAuthorize("@perm.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }

    @ApiOperation("分页查询分类列表")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        return categoryService.list(pageNum, pageSize, categoryDto);
    }

    @ApiOperation("新增分类")
    @PostMapping
    public ResponseResult saveCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @ApiOperation("根据id查询分类")
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @ApiOperation("更新分类")
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }
}
