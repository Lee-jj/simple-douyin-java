package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.leechee.entity.Comments;

@Mapper
public interface CommentMapper {

    /**
     * 插入评论
     * @param comments
     */
    @Insert("insert into comments (user_id, video_id, content, create_date) values (#{user_id}, #{video_id}, #{content}, #{create_date})")
    void insert(Comments comments);

    /**
     * 根据id删除评论
     * @param id
     */
    @Delete("delete from comments where id = #{id}")
    void delete(Long id);

    /**
     * 根据id获取评论
     * @param id
     * @return
     */
    @Select("select * from comments where id = #{id}")
    Comments getById(Long id);

    /**
     * 根据视频id获取评论列表
     * @param video_id
     * @return
     */
    @Select("select * from comments where video_id = #{video_id}")
    List<Comments> getByVideoId(Long video_id);
}
