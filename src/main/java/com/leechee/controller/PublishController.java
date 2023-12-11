package com.leechee.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leechee.constant.MessageConstant;
import com.leechee.result.Result;
import com.leechee.utils.QiniuOssUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/publish")
@Slf4j
public class PublishController {

    @Autowired
    private QiniuOssUtil qiniuOssUtil;
    
    @PostMapping("/action")
    public Result action(@RequestPart("data") MultipartFile file, @RequestPart("token") String token, @RequestParam("title") String title) {
        log.info("文件上传标题,{}", title);
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            // 获取文件请求路径
            String filePath = qiniuOssUtil.upload(file.getBytes(), objectName);

            log.info("文件上传到：{}", filePath);
            return Result.success();
        } catch (IOException e) {
            log.error("文件上传失败，{}", e);
        }
        return Result.error(MessageConstant.FILE_UPLOAD_FILED);
    }
}