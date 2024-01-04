package com.leechee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.annotation.OperationLog;
import com.leechee.dto.RelationDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.result.FriendResult;
import com.leechee.result.RelationResult;
import com.leechee.result.Result;
import com.leechee.service.RelationService;
import com.leechee.vo.FriendUserVO;
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
    @OperationLog(description = "关注与取消关注")
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
    @OperationLog(description = "获取用户关注列表")
    public RelationResult followList(UserInfoDTO userInfoDTO) {
        log.info("获取用户关注列表，{}", userInfoDTO);
        List<UserVO> userList = relationService.followList(userInfoDTO);
        return RelationResult.success(userList);
    }

    /**
     * 获取用户粉丝列表
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/follower/list/")
    @OperationLog(description = "获取用户粉丝列表")
    public RelationResult followerList(UserInfoDTO userInfoDTO) {
        log.info("获取用户粉丝列表，{}", userInfoDTO);
        List<UserVO> userList = relationService.followerList(userInfoDTO);
        return RelationResult.success(userList);
    }

    /**
     * 获取用户好友列表
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/friend/list/")
    @OperationLog(description = "获取用户好友列表")
    public FriendResult friendList(UserInfoDTO userInfoDTO) {
        log.info("获取用户好友列表,{}", userInfoDTO);
        List<FriendUserVO> userList = relationService.friendList(userInfoDTO);
        return FriendResult.success(userList);
    }
}
