package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessageChatDTO implements Serializable {
    
    // 用户鉴权token
    private String token;

    // 用户ID
    private Long to_user_id;

    // 上次最新消息的时间（新增字段-apk更新中）
    private Long pre_msg_time;
}
