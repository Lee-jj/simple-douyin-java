package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 更新视频获赞总数
     * @param video_id
     * @param count
     */
    @Update("update videos set favorite_count = favorite_count + #{count} where id = #{video_id}")
    void updateFavoriteCount(Long video_id, Integer count);

    /**
     * 根据视频id获取用户id
     * @param video_id
     * @return
     */
    @Select("select user_id from videos where id = #{video_id}")
    Long getUserIdByVideoId(Long video_id);
}
