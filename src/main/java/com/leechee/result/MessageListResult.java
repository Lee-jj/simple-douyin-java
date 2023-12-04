package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.MessageVO;

import lombok.Data;

@Data
public class MessageListResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 消息列表
    private List<MessageVO> message_list;

    public static MessageListResult success(List<MessageVO> object) {
        MessageListResult result = new MessageListResult();
        result.status_code = 0;
        result.message_list = object;
        return result;
    }

    public static MessageListResult error(String msg) {
        MessageListResult result = new MessageListResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
