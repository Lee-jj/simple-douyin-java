package com.leechee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.MessageDTO;
import com.leechee.result.Result;
import com.leechee.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/message")
@Slf4j
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    /**
     * 发送消息
     * @param messageDTO
     * @return
     */
    @PostMapping("/action/")
    public Result action(MessageDTO messageDTO) {
        log.info("发送消息,{}", messageDTO);
        messageService.action(messageDTO);
        return Result.success();
    }
}
