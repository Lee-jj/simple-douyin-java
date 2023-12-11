package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leechee.dto.FeedDTO;
import com.leechee.entity.Videos;

@Mapper
public interface VideoMapper {

    /**
     * 根据时间获取前30个视频
     * @param feedDTO
     * @return
     */
    List<Videos> getByCreateTime(FeedDTO feedDTO);
    
    /**
     * 新增视频
     * @param videos
     */
    void insert(Videos videos);
}
