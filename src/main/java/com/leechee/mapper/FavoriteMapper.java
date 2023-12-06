package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.entity.Favorites;

@Mapper
public interface FavoriteMapper {

    /**
     * 根据用户id和视频id动态查询点赞信息
     * @param favoriteSearchDTO
     * @return
     */
    List<Favorites> getById(FavoriteSearchDTO favoriteSearchDTO);
    
}
