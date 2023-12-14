package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.leechee.dto.RelationSearchDTO;
import com.leechee.entity.Relations;

@Mapper
public interface RelationMapper {

    /**
     * 根据id动态获取用户信息
     * @param relationSearchDTO
     * @return
     */
    List<Relations> getById(RelationSearchDTO relationSearchDTO);
    
    /**
     * 插入关注数据
     * @param relationSearchDTO
     */
    @Insert("insert into relations (from_user_id, to_user_id) values (#{from_user_id}, #{to_user_id})")
    void insert(RelationSearchDTO relationSearchDTO);

    /**
     * 根据id删除关注数据
     * @param id
     */
    @Delete("delete from relations where id = #{id}")
    void delete(Long id);

    /**
     * 获取当前用户的朋友（与id互相关注）
     * @param userId
     * @return
     */
    List<Long> getCommonByCurrentId(Long userId);
}
