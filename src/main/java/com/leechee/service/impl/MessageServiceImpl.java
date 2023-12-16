package com.leechee.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.MessageChatDTO;
import com.leechee.dto.MessageDTO;
import com.leechee.dto.MessageSearchDTO;
import com.leechee.entity.Messages;
import com.leechee.exception.MessageException;
import com.leechee.mapper.MessageMapper;
import com.leechee.service.MessageService;
import com.leechee.vo.MessageVO;

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

    /**
     * 获取聊天记录
     * @param messageChatDTO
     * @return
     */
    @Override
    public List<MessageVO> list(MessageChatDTO messageChatDTO) {
        Long currentId = BaseContext.getCurrentId();
        MessageSearchDTO messageSearchDTO = new MessageSearchDTO();
        messageSearchDTO.setFrom_user_id(currentId);
        messageSearchDTO.setTo_user_id(messageChatDTO.getTo_user_id());
        Long msg_time = messageChatDTO.getPre_msg_time();
        if (msg_time != null) {
            messageSearchDTO.setCreate_time(LocalDateTime.ofInstant(Instant.ofEpochMilli(msg_time), ZoneId.systemDefault()));
        }

        List<Messages> messagesList = messageMapper.getByUserId(messageSearchDTO);

        List<MessageVO> messageVOs = new ArrayList<>();
        for (Messages messages : messagesList) {
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(messages, messageVO);
            messageVOs.add(messageVO);
        }

        return messageVOs;
    }
    
}
