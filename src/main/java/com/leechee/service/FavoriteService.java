package com.leechee.service;

import java.util.List;

import com.leechee.dto.FavoriteDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.vo.VideoVO;

public interface FavoriteService {

    /**
     * 点赞操作
     * @param favoriteDTO
     * @return
     */
    void action(FavoriteDTO favoriteDTO);

    /**
     * 获取用户点赞列表
     * @param userInfoDTO
     * @return
     */
    List<VideoVO> list(UserInfoDTO userInfoDTO);
    
}
