package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.TagListDto;
import com.dm.blog.domain.entity.Tag;


/**
 * 标签(tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-08 14:10:13
 */
public interface TagService extends IService<Tag> {

    ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto);


    ResponseResult tag(TagListDto tagListDto);

    ResponseResult deleteTagByTd(Long id);

    ResponseResult getTagByTd(Long id);

    ResponseResult updateTagByTd(TagListDto tagListDto);

    ResponseResult listAllTag();
}
