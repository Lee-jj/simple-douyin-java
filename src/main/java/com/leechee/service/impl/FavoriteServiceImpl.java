package com.leechee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.FavoriteDTO;
import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.entity.Favorites;
import com.leechee.exception.FavoriteException;
import com.leechee.mapper.FavoriteMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;

    /**
     * 点赞操作
     * @param favoriteDTO
     * @return
     */
    @Transactional
    @Override
    public void action(FavoriteDTO favoriteDTO) {
        
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        Long videoId = favoriteDTO.getVideo_id();
        // 查找视频所属用户
        Long authorId = videoMapper.getUserIdByVideoId(videoId);

        // 查找点赞记录
        FavoriteSearchDTO favoriteSearchDTO = new FavoriteSearchDTO();
        favoriteSearchDTO.setUser_id(currentId);
        favoriteSearchDTO.setVideo_id(videoId);
        List<Favorites> favoritesList = favoriteMapper.getById(favoriteSearchDTO);
        Favorites favorites = null;
        if (favoritesList != null && favoritesList.size() > 0)
            favorites = favoritesList.get(0);
        
        if (favoriteDTO.getAction_type().equals(StatusConstant.DO)) {
            // 执行的是点赞操作
            if (favorites == null) {
                // 插入点赞记录
                favoriteMapper.insert(favoriteSearchDTO);

                // 同步更新视频点赞数
                videoMapper.updateFavoriteCount(videoId, 1);

                // 同步更新用户获得总赞数与点赞总数
                userMapper.updateFavoriteCount(currentId, 1);
                userMapper.updateTotalFavorited(authorId, 1);

            } else {
                // 已经点过赞了
                throw new FavoriteException(MessageConstant.DONOT_REPEAT_ACTION);
            }
        } else if (favoriteDTO.getAction_type().equals(StatusConstant.CANCEL)) {
            // 执行的是取消点赞操作
            if (favorites != null) {
                // 删除点赞记录
                favoriteMapper.delete(favorites.getId());

                // 同步更新视频点赞数
                videoMapper.updateFavoriteCount(videoId, -1);

                // 同步更新用户获得总赞数与点赞总数
                userMapper.updateFavoriteCount(currentId, -1);
                userMapper.updateTotalFavorited(authorId, -1);

            } else {
                // 没有点过赞
                throw new FavoriteException(MessageConstant.DONOT_REPEAT_CANCEL);
            }
        } else {
            throw new FavoriteException(MessageConstant.ERROR_ACTION_TYPE);
        }
    }
    
}
