package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据用户id获取视频列表
     * @param user_id
     * @return
     */
    @Select("select * from videos where user_id = #{user_id}")
    List<Videos> getByUserId(Long user_id);
}
