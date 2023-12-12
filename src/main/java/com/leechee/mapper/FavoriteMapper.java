package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.entity.Favorites;

@Mapper
public interface FavoriteMapper {

    /**
     * 根据用户id和视频id动态查询点赞信息
     * @param favoriteSearchDTO
     * @return
     */
    List<Favorites> getById(FavoriteSearchDTO favoriteSearchDTO);
    
    /**
     * 插入点赞信息
     * @param favoriteSearchDTO
     */
    @Insert("insert into favorites (user_id, video_id) values (#{user_id}, #{video_id})")
    void insert(FavoriteSearchDTO favoriteSearchDTO);

    /**
     * 根据id删除点赞记录
     * @param id
     */
    @Delete("delete from favorites where id = #{id}")
    void delete(Long id);
}
