package com.leechee.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PublishDTO implements Serializable{

    // 视频数据
    private MultipartFile data;

    // 用户鉴权token
    private String token;

    // 视频标题
    private String title;
}
