package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.UserVO;

import lombok.Data;

@Data
public class RelationResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 用户信息列表
    private List<UserVO> user_list;

    public static RelationResult success(List<UserVO> userList) {
        RelationResult result = new RelationResult();
        result.status_code = 0;
        result.user_list = userList;
        return result;
    }

    public static RelationResult error(String msg) {
        RelationResult result = new RelationResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }

}
