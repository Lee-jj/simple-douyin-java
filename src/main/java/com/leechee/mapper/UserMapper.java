package com.leechee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    
}
