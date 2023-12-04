package com.leechee.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Long id;

    // 姓名
    private String name;

    // 密码
    private String password;

    // 头像链接
    private String avatar;

    // 背景链接
    private String background_image;

    // 简介
    private String signature;

    // 关注数
    private Long follow_count;

    // 粉丝数
    private Long follower_count;

    // 获赞数
    private Long total_favorited;

    // 点赞数
    private Long favorite_count;

    // 作品数
    private Long work_count;

}
