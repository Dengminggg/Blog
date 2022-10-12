package com.dm.blog.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.CategoryDto;
import com.dm.blog.domain.entity.Article;
import com.dm.blog.domain.entity.Category;
import com.dm.blog.domain.entity.DownloadData;
import com.dm.blog.domain.vo.CategoryVo;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.mapper.CategoryMapper;
import com.dm.blog.service.ArticleService;
import com.dm.blog.service.CategoryService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-09-28 12:41:02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult<Category> getCategoryList() {
        //查询文章表中状态为已发布的文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(wrapper);

        //使用stream流获取文章的分类id并去重
        Set<Long> set = list.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(set);

        //利用stream流提取正常状态的分类
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //bean拷贝
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        //返回响应封装对象
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        //需要判别为正常状态下
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> list = list();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @Override
    public void export(HttpServletResponse response) {

        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader(SystemConstants.CATEGORY_NAME, response);

            //获取分类数据
            List<Category> categoryList = categoryService.list();
            List<DownloadData> downloadData = BeanCopyUtils.copyBeanList(categoryList, DownloadData.class);

            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), DownloadData.class)
                    .sheet("Category")
                    .doWrite(downloadData);

        } catch (Exception e) {
            //如果出现异常
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }


    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        //过滤条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(categoryDto.getName()), Category::getName, categoryDto.getName());
        queryWrapper.eq(Objects.nonNull(categoryDto.getStatus()), Category::getStatus, categoryDto.getStatus());

        //分页
        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        page(categoryPage, queryWrapper);

        //转换成VO
        List<Category> categories = categoryPage.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        PageVo<CategoryVo> vo = new PageVo<>(categoryVos, categoryPage.getTotal());
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult saveCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }


}

