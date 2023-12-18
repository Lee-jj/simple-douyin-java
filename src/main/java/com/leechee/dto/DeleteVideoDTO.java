package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeleteVideoDTO implements Serializable {
    
    // 用户鉴权token
    private String token;

    // 视频id
    private Long video_id;
}
