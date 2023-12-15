package com.leechee.service;

import com.leechee.dto.MessageDTO;

public interface MessageService {

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    void action(MessageDTO messageDTO);
    
}
