package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.leechee.entity.Users;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    @Select("select * from users where name = #{name}")
    Users getByUsername(String name);

    /**
     * 插入用户
     * @param users
     */
    void insert(Users users);

    /**
     * 根据id查询用户
     * @param user_id
     * @return
     */
    @Select("select * from users where id = #{user_id}")
    Users getById(Long user_id);

    /**
     * 上传视频后，更新用户作品数量
     * @param currentId
     */
    @Update("update users set work_count = work_count + #{count} where id = #{currentId}")
    void updateWorkCount(Long currentId, Integer count);
    
    /**
     * 更新用户点赞总数
     * @param user_id
     * @param count
     */
    @Update("update users set favorite_count = favorite_count + #{count} where id = #{user_id}")
    void updateFavoriteCount(Long user_id, Integer count);

    /**
     * 更新用户获赞总数
     * @param user_id
     * @param count
     */
    @Update("update users set total_favorited = total_favorited + #{count} where id = #{user_id}")
    void updateTotalFavorited(Long user_id, Long count);

    /**
     * 更新用户关注总数
     * @param user_id
     * @param count
     */
    @Update("update users set follow_count = follow_count + #{count} where id = #{user_id}")
    void updateFollowCount(Long user_id, Integer count);

    /**
     * 更新用户粉丝总数
     * @param user_id
     * @param count
     */
    @Update("update users set follower_count = follower_count + #{count} where id = #{user_id}")
    void updateFollowerCount(Long user_id, Integer count);

    /**
     * 根据用户id批量更新用户点赞总数
     * @param user_ids
     * @param count
     */
    void updateFavoriteCountBatch(List<Long> ids, Long count);
}
