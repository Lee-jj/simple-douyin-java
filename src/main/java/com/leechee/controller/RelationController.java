package com.leechee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.RelationDTO;
import com.leechee.result.Result;
import com.leechee.service.RelationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/relation")
@Slf4j
public class RelationController {
    
    @Autowired
    private RelationService relationService;

    /**
     * 关注与取消关注
     * @param relationDTO
     * @return
     */
    @PostMapping("/action/")
    public Result action(RelationDTO relationDTO) {
        log.info("关注与取消关注操作,{}", relationDTO);
        relationService.action(relationDTO);
        return Result.success();
    }

}
