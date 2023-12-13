package com.leechee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.dto.CommentActionDTO;
import com.leechee.result.CommentActionResult;
import com.leechee.service.CommentService;
import com.leechee.vo.CommentVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/comment")
@Slf4j
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    /**
     * 添加或删除评论
     * @param commentActionDTO
     * @return
     */
    @PostMapping("/action/")
    public CommentActionResult action(CommentActionDTO commentActionDTO) {
        log.info("添加或删除评论,{}", commentActionDTO);
        CommentVO comment = commentService.action(commentActionDTO);
        return CommentActionResult.success(comment);
    }
}
