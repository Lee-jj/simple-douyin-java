package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.FriendUserVO;

import lombok.Data;

@Data
public class FriendResult implements Serializable {
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 用户列表
    private List<FriendUserVO> user_list;

    public static FriendResult success(List<FriendUserVO> object) {
        FriendResult result = new FriendResult();
        result.status_code = 0;
        result.user_list = object;
        return result;
    }

    public static FriendResult error(String msg) {
        FriendResult result = new FriendResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
