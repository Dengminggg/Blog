package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.CategoryDto;
import com.dm.blog.domain.entity.Category;

import javax.servlet.http.HttpServletResponse;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-28 12:41:02
 */
public interface CategoryService extends IService<Category> {

    ResponseResult<Category> getCategoryList();

    ResponseResult listAllCategory();

    void export(HttpServletResponse response);

    ResponseResult list(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    ResponseResult saveCategory(CategoryDto categoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult deleteCategory(Long id);
}
