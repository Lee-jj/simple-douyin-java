package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FavoriteDTO implements Serializable {
    
    // 用户鉴权token
    private String token;

    // 视频id
    private Long video_id;

    // 1-点赞，2-取消点赞
    private Integer action_type;
}
