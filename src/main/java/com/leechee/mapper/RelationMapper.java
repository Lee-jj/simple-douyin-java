package com.leechee.mapper;

import java.util.List;

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
    
}
