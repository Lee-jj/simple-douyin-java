package com.leechee.result;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;     

    // 用户id
    private Long user_id;

    // 用户鉴权token
    private String token;

    public static UserResult success(Long userId, String token) {
        UserResult result = new UserResult();
        result.status_code = 0;
        result.user_id = userId;
        result.token = token;
        return result;
    }

    public static UserResult error(String msg) {
        UserResult result = new UserResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
