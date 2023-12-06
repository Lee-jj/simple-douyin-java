package com.leechee.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FeedDTO implements Serializable {
    
    // 可选参数，限制返回视频的最新投稿时间戳，精确到秒，不填表示当前时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Long latest_time;

    // 用户登录状态下设置
    private String token;
}
