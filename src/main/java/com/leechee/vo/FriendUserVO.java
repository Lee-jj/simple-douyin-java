package com.leechee.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendUserVO extends UserVO implements Serializable{

    // 和该好友的最新聊天消息
    private String message;

    // message消息的类型，0 => 当前请求用户接收的消息， 1 => 当前请求用户发送的消息
    private Long msgType;
}
