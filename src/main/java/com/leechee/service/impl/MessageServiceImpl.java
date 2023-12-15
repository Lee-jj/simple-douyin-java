package com.leechee.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.MessageDTO;
import com.leechee.entity.Messages;
import com.leechee.exception.MessageException;
import com.leechee.mapper.MessageMapper;
import com.leechee.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    @Override
    public void action(MessageDTO messageDTO) {
        Long currentId = BaseContext.getCurrentId();

        if (messageDTO.getAction_type().equals(StatusConstant.DO)) {

            Messages messages = Messages.builder()
                   .from_user_id(currentId)
                   .to_user_id(messageDTO.getTo_user_id())
                   .content(messageDTO.getContent())
                   .create_time(LocalDateTime.now())
                   .build();
            messageMapper.insert(messages);
        } else {
            throw new MessageException(MessageConstant.ERROR_ACTION_TYPE);
        }
    }
    
}
