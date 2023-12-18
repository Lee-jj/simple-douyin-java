package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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

    /**
     * 根据id获得视频
     * @param id
     * @return
     */
    @Select("select * from videos where id = #{id}")
    Videos getById(Long id);

    /**
     * 更新视频评论总数
     * @param video_id
     * @param count
     */
    @Update("update videos set comment_count = comment_count + #{count} where id = #{video_id}")
    void updateCommentCount(Long video_id, Integer count);
    
    /**
     * 根据id删除视频
     * @param id
     */
    @Delete("delete from videos where id = #{id}")
    void deleteById(Long id);
}
