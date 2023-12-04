package com.leechee.result;

import java.io.Serializable;

import com.leechee.vo.UserVO;

import lombok.Data;

@Data
public class UserInfoResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;    

    // 用户信息
    private UserVO user;

    public static UserInfoResult success(UserVO user) {
        UserInfoResult result = new UserInfoResult();
        result.status_code = 0;
        result.user = user;
        return result;
    }

    public static UserInfoResult error(String msg) {
        UserInfoResult result = new UserInfoResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
