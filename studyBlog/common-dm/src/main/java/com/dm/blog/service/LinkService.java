package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.LinkDto;
import com.dm.blog.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-09-29 13:58:10
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult addLink(LinkDto linkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLinkById(Long id);
}
