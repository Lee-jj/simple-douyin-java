package com.leechee.service;

import java.util.List;

import com.leechee.dto.FeedDTO;
import com.leechee.vo.VideoVO;


public interface FeedService {

    /**
     * 获取视频流
     * @param feedDTO
     * @return
     */
    List<VideoVO> getFeed(FeedDTO feedDTO);
    
}
