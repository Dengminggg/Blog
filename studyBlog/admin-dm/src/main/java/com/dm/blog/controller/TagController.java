package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.TagListDto;
import com.dm.blog.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("分页查询标签")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.list(pageNum, pageSize, tagListDto);
    }

    @ApiOperation("查询所有标签")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

    @ApiOperation("新增标签")
    @PostMapping
    public ResponseResult tag(@RequestBody TagListDto tagListDto) {
        return tagService.tag(tagListDto);
    }

    @ApiOperation("删除标签")
    @DeleteMapping("/{id}")
    public ResponseResult deleteTagByTd(@PathVariable("id") Long id) {
        return tagService.deleteTagByTd(id);
    }

    @ApiOperation("获取标签")
    @GetMapping("/{id}")
    public ResponseResult getTagByTd(@PathVariable("id") Long id) {
        return tagService.getTagByTd(id);
    }

    @ApiOperation("修改标签")
    @PutMapping
    public ResponseResult updateTagByTd(@RequestBody TagListDto tagListDto) {
        return tagService.updateTagByTd(tagListDto);
    }
}
