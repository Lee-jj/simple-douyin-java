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
public class UserVO implements Serializable{
    
    // 用户id
    private Long id;

    // 用户名称
    private String name;

    // 关注总数
    private Long follow_count;

    // 粉丝总数
    private Long follower_count;

    // true-已关注，false-未关注
    private boolean is_follow;

    // 用户头像
    private String avatar;

    // 用户个人页顶部大图
    private String background_image;

    // 个人简介
    private String signature;

    // 获赞数量
    private Long total_favorited;

    // 作品数量
    private Long work_count;

    // 点赞数量
    private Long favorite_count;

    public boolean getIs_follow() {
        return is_follow;
    }

}
