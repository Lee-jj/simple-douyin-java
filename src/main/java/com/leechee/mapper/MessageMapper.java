package com.leechee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.leechee.dto.MessageSearchDTO;
import com.leechee.entity.Messages;

@Mapper
public interface MessageMapper {
    
    @Insert("insert into messages (from_user_id, to_user_id, content, create_time) values (#{from_user_id}, #{to_user_id}, #{content}, #{create_time})")
    void insert(Messages messages);

    /**
     * 根据from_user_id和to_user_id查询消息
     * @param messageSearchDTO
     * @return
     */
    List<Messages> getByUserId(MessageSearchDTO messageSearchDTO);
}
