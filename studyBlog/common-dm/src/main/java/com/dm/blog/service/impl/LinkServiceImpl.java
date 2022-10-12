package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.LinkDto;
import com.dm.blog.domain.entity.Link;
import com.dm.blog.domain.vo.LinkVo;
import com.dm.blog.domain.vo.PageVo;
import com.dm.blog.mapper.LinkMapper;
import com.dm.blog.service.LinkService;
import com.dm.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-09-29 13:58:10
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核为通过状态的友链
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_PASS);
        List<Link> list = list(wrapper);

        //转换成vo对象
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        //过滤条件
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper();
        wrapper.like(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName());
        wrapper.eq(Objects.nonNull(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());

        //分页
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        //转换VO
        List<Link> links = page.getRecords();
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        PageVo pageVo = new PageVo(linkVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLinkById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

