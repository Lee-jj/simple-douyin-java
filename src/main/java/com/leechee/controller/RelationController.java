package com.leechee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.RelationDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.result.RelationResult;
import com.leechee.result.Result;
import com.leechee.service.RelationService;
import com.leechee.vo.UserVO;

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

    /**
     * 获取用户关注列表
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/follow/list/")
    public RelationResult followList(UserInfoDTO userInfoDTO) {
        log.info("获取用户关注列表，{}", userInfoDTO);
        List<UserVO> userList = relationService.followList(userInfoDTO);
        return RelationResult.success(userList);
    }
}
