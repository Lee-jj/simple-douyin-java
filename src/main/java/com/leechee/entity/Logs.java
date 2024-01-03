package com.leechee.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Logs implements Serializable{
 
    private static final long serialVersionUID = 1L;

    private Long id;

    private String operation_user_id; // 操作者id

    private LocalDateTime operation_date; // 操作时间

    private String class_name; // 操作类名

    private String method_name; // 操作方法名

    private String request_type; // 请求类型

    private String ip_address;  // 操作ip地址

    private String url; // 访问url

    private Long cost_time;  // 请求执行耗时，单位ms

    private String description; //  操作描述
}
