package com.dm.blog.service.impl;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.exception.SystemException;
import com.dm.blog.service.UploadService;
import com.dm.blog.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    //生成上传凭证
    private String bucket;
    private String accessKey;
    private String secretKey;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        //获取文件原始名称
        String originalFilename = img.getOriginalFilename();
        //对原名称进行后缀判定，只有'.png'可以通过上传
        if (!originalFilename.endsWith(".png")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    /**
     * 七牛云上传（InputStream流）代码
     *
     * @param img      文件
     * @param filePath 上传到的位置
     * @return 外链地址
     */
    private String uploadOss(MultipartFile img, String filePath) {
        //构造一个带指定 Region 地区对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "http://rjbnc5vri.hn-bkt.clouddn.com/" + key;

    }

}
