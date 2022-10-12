package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.TagListDto;
import com.dm.blog.domain.entity.Tag;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.domain.vo.TagVo;
import com.dm.blog.mapper.TagMapper;
import com.dm.blog.service.TagService;
import com.dm.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签(tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-08 14:10:13
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        //封装查询条件：根据标签名或备注
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        //分页查询
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        //封装数据返回
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo<Tag> pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult tag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagByTd(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagByTd(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTagByTd(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        //根据前后端协议过滤掉部分数据
        tagVos = tagVos.stream()
                .map(tagVo -> new TagVo(tagVo.getId(), tagVo.getName(), null))
                .collect(Collectors.toList());
        return ResponseResult.okResult(tagVos);
    }

}