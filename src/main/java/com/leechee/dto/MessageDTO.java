package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessageDTO implements Serializable{
    
    // 用户鉴权token
    private String token;

    // 对方用户id
    private Long to_user_id;

    // 1-发送消息
    private Integer action_type;

    // 消息内容
    private String content;
}
