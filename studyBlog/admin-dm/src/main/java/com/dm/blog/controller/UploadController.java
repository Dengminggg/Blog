package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
