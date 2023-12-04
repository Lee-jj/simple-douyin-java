package com.leechee.result;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;   

    public static Result success() {
        Result result = new Result();
        result.status_code = 0;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
