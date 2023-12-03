package com.leechee.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO implements Serializable{
    
    // 视频唯一标识
    private Long id;

    // 视频作者信息
    private UserVO author;

    // 视频播放地址
    private String play_url;

    // 视频封面地址
    private String cover_url;

    // 视频的点赞总数
    private Long favorite_count;

    // 视频的评论总数
    private Long comment_count;

    // true-已点赞，false-未点赞
    private boolean is_favorite;

    // 视频标题
    private String title;
}
