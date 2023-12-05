package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfoDTO implements Serializable {
    
    // 用户id
    private Long user_id;

    // 用户鉴权token
    private String token;
}
