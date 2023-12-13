package com.leechee.service;

import java.util.List;

import com.leechee.dto.CommentActionDTO;
import com.leechee.dto.CommentListDTO;
import com.leechee.vo.CommentVO;

public interface CommentService {

    /**
     * 添加或删除评论
     * @param commentActionDTO
     * @return
     */
    CommentVO action(CommentActionDTO commentActionDTO);

    /**
     * 获取视频评论列表
     * @param commentListDTO
     * @return
     */
    List<CommentVO> list(CommentListDTO commentListDTO);
    
}
