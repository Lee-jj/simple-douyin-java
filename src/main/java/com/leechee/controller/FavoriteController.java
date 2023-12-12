package com.leechee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.FavoriteDTO;
import com.leechee.result.Result;
import com.leechee.service.FavoriteService;

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
}
