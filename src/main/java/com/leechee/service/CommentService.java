package com.leechee.service;

import com.leechee.dto.CommentActionDTO;
import com.leechee.vo.CommentVO;

public interface CommentService {

    /**
     * 添加或删除评论
     * @param commentActionDTO
     * @return
     */
    CommentVO action(CommentActionDTO commentActionDTO);
    
}
