package com.leechee.service;

import java.util.List;

import com.leechee.dto.MessageChatDTO;
import com.leechee.dto.MessageDTO;
import com.leechee.vo.MessageVO;

public interface MessageService {

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    void action(MessageDTO messageDTO);

    /**
     * 获取聊天记录
     * @param messageChatDTO
     * @return
     */
    List<MessageVO> list(MessageChatDTO messageChatDTO);
    
}
