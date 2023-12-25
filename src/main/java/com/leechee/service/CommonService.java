package com.leechee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.dto.RelationSearchDTO;
import com.leechee.entity.Favorites;
import com.leechee.entity.Relations;
import com.leechee.mapper.FavoriteMapper;
import com.leechee.mapper.RelationMapper;

@Service
public class CommonService {
    
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;

    /**
     * 判断是否关注
     * @param currentId 当前登录用户
     * @param targetId  目标用户
     * @return
     */
    public Boolean getRelation(Long currentId, Long targetId) {
        RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
        relationSearchDTO.setFrom_user_id(currentId);
        relationSearchDTO.setTo_user_id(targetId);
        List<Relations> relations = relationMapper.getById(relationSearchDTO);
        if (relations != null && relations.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否点赞
     * @param currentId 当前登录用户
     * @param videoId   目标视频
     * @return
     */
    public Boolean getFavorite(Long currentId, Long videoId) {
        FavoriteSearchDTO favoriteSearchDTO = new FavoriteSearchDTO();
        favoriteSearchDTO.setUser_id(currentId);
        favoriteSearchDTO.setVideo_id(videoId);
        List<Favorites> favorites = favoriteMapper.getById(favoriteSearchDTO);
        if (favorites != null && favorites.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
