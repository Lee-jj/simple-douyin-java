package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelationDTO implements Serializable{
    
    // 用户鉴权token
    private String token;

    // 对方用户id
    private String to_user_id;

    // 1-关注，2-取消关注
    private String action_type;
}
