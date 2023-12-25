package com.leechee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.FavoriteDTO;
import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.entity.Favorites;
import com.leechee.entity.Users;
import com.leechee.entity.Videos;
import com.leechee.exception.FavoriteException;
import com.leechee.mapper.FavoriteMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.CommonService;
import com.leechee.service.FavoriteService;
import com.leechee.vo.UserVO;
import com.leechee.vo.VideoVO;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CommonService commonService;

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
                userMapper.updateTotalFavorited(authorId, (long) 1);

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
                userMapper.updateTotalFavorited(authorId, (long) -1);

            } else {
                // 没有点过赞
                throw new FavoriteException(MessageConstant.DONOT_REPEAT_CANCEL);
            }
        } else {
            throw new FavoriteException(MessageConstant.ERROR_ACTION_TYPE);
        }
    }


    /**
     * 获取用户点赞列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<VideoVO> list(UserInfoDTO userInfoDTO) {

        Long user_id = userInfoDTO.getUser_id();
        Long currentId = BaseContext.getCurrentId();

        // 从点赞表中查询指定用户的点赞记录
        FavoriteSearchDTO favoriteSearchDTO = new FavoriteSearchDTO();
        favoriteSearchDTO.setUser_id(user_id);
        List<Favorites> favoritesList = favoriteMapper.getById(favoriteSearchDTO);

        // 构造返回对象
        List<VideoVO> videoList = new ArrayList<>();
        for (Favorites favorites: favoritesList) {

            // 构造video对象
            Long video_id = favorites.getVideo_id();
            VideoVO videoVO = new VideoVO();
            Videos videosDB = videoMapper.getById(video_id);
            BeanUtils.copyProperties(videosDB, videoVO);

            // 构造user对象
            Long authorId = videosDB.getUser_id();
            Users usersDB = userMapper.getById(authorId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(usersDB, userVO);

            // 设置关注信息
            userVO.set_follow(commonService.getRelation(currentId, authorId));

            // 查找点赞信息
            videoVO.set_favorite(commonService.getFavorite(currentId, video_id));

            videoVO.setAuthor(userVO);
            videoList.add(videoVO);
        }

        return videoList;
    }
    
}
