package com.leechee.result;

import java.io.Serializable;

import com.leechee.vo.CommentVO;

import lombok.Data;

@Data
public class CommentActionResult implements Serializable {
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 评论成功返回评论内容，不需要重新拉取整个列表
    private CommentVO comment;

    public static CommentActionResult success(CommentVO comment) {
        CommentActionResult result = new CommentActionResult();
        result.status_code = 0;
        result.comment = comment;
        return result;
    }

    public static CommentActionResult error(String msg) {
        CommentActionResult result = new CommentActionResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
