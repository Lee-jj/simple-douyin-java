package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.CommentVO;

import lombok.Data;

@Data
public class CommentListResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 评论列表
    private List<CommentVO> comment_list;

    public static CommentListResult success(List<CommentVO> object) {
        CommentListResult result = new CommentListResult();
        result.status_code = 0;
        result.comment_list = object;
        return result;
    }

    public static CommentListResult error(String msg) {
        CommentListResult result = new CommentListResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
