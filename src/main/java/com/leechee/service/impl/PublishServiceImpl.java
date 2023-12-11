package com.leechee.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leechee.context.BaseContext;
import com.leechee.entity.Videos;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.PublishService;

@Service
public class PublishServiceImpl implements PublishService{

    @Autowired
    private VideoMapper videoMapper;

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
    
}
