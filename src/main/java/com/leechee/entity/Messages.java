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
public class Messages implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    // 该消息接收者的id
    private Long to_user_id;

    // 该消息发送者的id
    private Long from_user_id;

    // 消息内容
    private String content;

    // 消息创建时间
    private LocalDateTime create_time;
}
