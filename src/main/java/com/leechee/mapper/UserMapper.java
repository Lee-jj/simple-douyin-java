package com.leechee.mapper;

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
     * 上传视频后，用户作品数量+1
     * @param currentId
     */
    @Update("update users set work_count = work_count + 1 where id = #{currentId}")
    void updateWorkCount(Long currentId);
    
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
    void updateTotalFavorited(Long user_id, Integer count);
}
