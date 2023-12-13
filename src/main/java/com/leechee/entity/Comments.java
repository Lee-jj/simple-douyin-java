package com.leechee.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    // 评论用户信息
    private Long user_id;

    // 视频id
    private Long video_id;

    // 评论内容
    private String content;

    // 评论发布日期，格式 mm-dd
    private LocalDateTime create_date;
}
