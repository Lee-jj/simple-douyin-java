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
public class MessageVO  implements Serializable{
    
    // 消息id
    private Long id;

    // 该消息接收者的id
    private Long to_user_id;

    // 该消息发送者的id
    private Long from_user_id;

    // 消息内容
    private String content;

    // 消息创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_time;


}
