package com.leechee.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Videos implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    
    // 提交时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Long create_time;

    // 用户id
    private Long user_id;

    // 视频链接id
    private String play_url;

    // 封面链接
    private String cover_url;

    // 标题
    private String title;

    // 该视频点赞数
    private Long favorite_count;

    // 视频评论数
    private Long comment_count;
}
