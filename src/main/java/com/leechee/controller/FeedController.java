package com.leechee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.FeedDTO;
import com.leechee.result.FeedResult;
import com.leechee.service.FeedService;
import com.leechee.vo.VideoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/feed")
@Slf4j
public class FeedController {

    @Autowired
    private FeedService feedService;
    
    /**
     * 获取视频流
     * @param feedDTO
     * @return
     */
    @GetMapping
    public FeedResult getFeed(FeedDTO feedDTO) {
        log.info("获取视频流,{}", feedDTO);
        List<VideoVO> videoList = feedService.getFeed(feedDTO);
        return FeedResult.success(videoList, feedDTO.getLatest_time());
    }
}
