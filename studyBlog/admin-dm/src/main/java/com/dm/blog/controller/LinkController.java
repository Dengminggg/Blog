package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.LinkDto;
import com.dm.blog.service.LinkService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("分页查询友链列表")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        return linkService.list(pageNum, pageSize, linkDto);
    }

    @ApiOperation("新增友链")
    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDto linkDto) {
        return linkService.addLink(linkDto);
    }

    @ApiOperation("根据id查询友联")
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id) {
        return linkService.getLinkById(id);
    }


    @ApiOperation("修改友链")
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDto linkDto) {
        return linkService.updateLink(linkDto);
    }

    @ApiOperation("删除友链")
    @DeleteMapping("/{id}")
    public ResponseResult deleteLinkById(@PathVariable("id") Long id) {
        return linkService.deleteLinkById(id);
    }
}
