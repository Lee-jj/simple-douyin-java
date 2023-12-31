package com.leechee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leechee.context.BaseContext;
import com.leechee.dto.FeedDTO;
import com.leechee.entity.Users;
import com.leechee.entity.Videos;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.CommonService;
import com.leechee.service.FeedService;
import com.leechee.vo.UserVO;
import com.leechee.vo.VideoVO;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;

    /**
     * 获取视频流
     * @param feedDTO
     * @return
     */
    @Override
    public List<VideoVO> getFeed(FeedDTO feedDTO) {
        // 从videos中查询从新到旧的30个视频
        List<Videos> videos = videoMapper.getByCreateTime(feedDTO);
        List<VideoVO> videoVOList = new ArrayList<>();
        
        for (Videos video: videos) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(video, videoVO);

            // 根据视频作者id查询详细作者信息
            Users user = userMapper.getById(video.getUser_id());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            Long currentId = BaseContext.getCurrentId();
            // 当前未登录
            if (currentId == null) {
                userVO.set_follow(false);
                videoVO.set_favorite(false);
            } else {
                // 判断当前登录用户是否关注视频作者
                userVO.set_follow(commonService.getRelation(currentId, video.getUser_id()));

                // 判断当前登录用户是否点赞视频
                videoVO.set_favorite(commonService.getFavorite(currentId, video.getId()));
            }

            videoVO.setAuthor(userVO);
            videoVOList.add(videoVO);
        }
        return videoVOList;
    }
    
}
