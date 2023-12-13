package com.leechee.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO implements Serializable{
    
    // 视频评论id
    private Long id;

    // 评论用户信息
    private UserVO user;

    // 评论内容
    private String content;

    // 评论发布日期，格式 mm-dd
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd")
    private LocalDateTime create_date;
}
