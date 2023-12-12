package com.leechee.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leechee.context.BaseContext;
import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.dto.RelationSearchDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.entity.Favorites;
import com.leechee.entity.Relations;
import com.leechee.entity.Users;
import com.leechee.entity.Videos;
import com.leechee.mapper.FavoriteMapper;
import com.leechee.mapper.RelationMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.PublishService;
import com.leechee.vo.UserVO;
import com.leechee.vo.VideoVO;

@Service
public class PublishServiceImpl implements PublishService{

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Value("${douyin.default.avatar}")
    private String defaultAvatar;

    /**
     * 上传视频
     * @param filePath
     * @param title
     */
    @Override
    public void action(String filePath, String title) {
        // TODO 截取上传视频的第一帧作为封面
        String coverUrl = defaultAvatar;
        Videos videos = Videos.builder()
               .title(title)
               .user_id(BaseContext.getCurrentId())
               .play_url(filePath)
               .cover_url(coverUrl)
               .create_time(LocalDateTime.now())
               .build();
        videoMapper.insert(videos);
    }

    /**
     * 获取用户发布列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<VideoVO> list(UserInfoDTO userInfoDTO) {
        // 从数据库获取用户视频列表
        List<Videos> videosList = videoMapper.getByUserId(userInfoDTO.getUser_id());   
        List<VideoVO> videoVOList = new ArrayList<>();
        
        for (Videos videos: videosList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(videos, videoVO);

            // 根据视频作者id查询用户信息
            Users videoAuthor = userMapper.getById(videos.getUser_id());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(videoAuthor, userVO);

            // 设置关注与点赞信息
            Long currentId = BaseContext.getCurrentId();

            // 查找关注信息
            RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
            relationSearchDTO.setFrom_user_id(currentId);
            relationSearchDTO.setTo_user_id(videos.getUser_id());
            List<Relations> relations = relationMapper.getById(relationSearchDTO);
            if (relations != null && relations.size() > 0) {
                userVO.set_follow(true);
            } else {
                userVO.set_follow(false);
            }

            // 查找点赞信息
            FavoriteSearchDTO favoriteSearchDTO = new FavoriteSearchDTO();
            favoriteSearchDTO.setUser_id(currentId);
            favoriteSearchDTO.setVideo_id(videos.getId());
            List<Favorites> favorites = favoriteMapper.getById(favoriteSearchDTO);
            if (favorites != null && favorites.size() > 0) {
                videoVO.set_favorite(true);
            } else {
                videoVO.set_favorite(false);
            }

            videoVO.setAuthor(userVO);
            videoVOList.add(videoVO);
        }
        
        return videoVOList;
    }
    
}
