package com.leechee.service;

import com.leechee.dto.FavoriteDTO;

public interface FavoriteService {

    /**
     * 点赞操作
     * @param favoriteDTO
     * @return
     */
    void action(FavoriteDTO favoriteDTO);
    
}
