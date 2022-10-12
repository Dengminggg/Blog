package com.dm.blog.controller;

import com.dm.blog.annotation.SystemLog;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @SystemLog(BusinessName = "上传头像")
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
