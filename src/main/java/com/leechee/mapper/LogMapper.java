package com.leechee.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.leechee.entity.Logs;

@Mapper
public interface LogMapper {
    
    /**
     * 插入操作日志
     * @param logs
     */
    @Insert("insert into logs (operation_user_id, operation_date, class_name, method_name, request_type, ip_address, url, cost_time, description) " 
    + "values (#{operation_user_id}, #{operation_date}, #{class_name}, #{method_name}, #{request_type}, #{ip_address}, #{url}, #{cost_time}, #{description})")
    void insert(Logs logs);
}
