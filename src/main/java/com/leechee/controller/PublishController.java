package com.leechee.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leechee.constant.MessageConstant;
import com.leechee.dto.UserInfoDTO;
import com.leechee.result.Result;
import com.leechee.result.VideoPublishResult;
import com.leechee.service.PublishService;
import com.leechee.utils.QiniuOssUtil;
import com.leechee.vo.VideoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/publish")
@Slf4j
public class PublishController {

    @Autowired
    private QiniuOssUtil qiniuOssUtil;
    @Autowired
    private PublishService publishService;
    
    /**
     * 上传视频
     * @param file
     * @param token
     * @param title
     * @return
     */
    @PostMapping("/action/")
    public Result action(@RequestPart("data") MultipartFile file, @RequestPart("token") String token, @RequestParam("title") String title) {
        log.info("文件上传标题,{}", title);
        try {
            // 1. 上传视频文件
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            // 获取文件请求路径
            String filePath = qiniuOssUtil.upload(file.getBytes(), objectName);

            log.info("视频文件上传到：{}", filePath);


            // 2. 上传抽帧的视频封面文件
            MultipartFile coverFile = publishService.getCoverFile(file);
            originalFilename = coverFile.getOriginalFilename();
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            objectName = UUID.randomUUID().toString() + extension;
            String coverPath = qiniuOssUtil.upload(coverFile.getBytes(), objectName);

            log.info("封面文件上传到: {}", coverPath);

            publishService.action(filePath, coverPath, title);

            return Result.success();
            
        } catch (IOException e) {
            log.error("文件上传失败，{}", e);
        }
        return Result.error(MessageConstant.FILE_UPLOAD_FILED);
    }

    /**
     * 获取用户发布列表
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/list/")
    public VideoPublishResult list(UserInfoDTO userInfoDTO) {
        log.info("获取用户发布列表，{}", userInfoDTO);
        List<VideoVO> videoList = publishService.list(userInfoDTO);
        return VideoPublishResult.success(videoList);
    }
}
