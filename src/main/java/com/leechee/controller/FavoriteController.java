package com.leechee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.FavoriteDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.result.FavoriteListResult;
import com.leechee.result.Result;
import com.leechee.service.FavoriteService;
import com.leechee.vo.VideoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/favorite")
@Slf4j
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    
    /**
     * 点赞操作
     * @param favoriteDTO
     * @return
     */
    @PostMapping("/action/")
    public Result action(FavoriteDTO favoriteDTO) {
        log.info("点赞与取消操作,{}", favoriteDTO);
        favoriteService.action(favoriteDTO);
        return Result.success();
    }

    /**
     * 获取用户点赞列表
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/list/")
    public FavoriteListResult list(UserInfoDTO userInfoDTO) {
        log.info("获取用户点赞列表，{}", userInfoDTO);
        List<VideoVO> videoList = favoriteService.list(userInfoDTO);
        return FavoriteListResult.success(videoList);
    }
}
