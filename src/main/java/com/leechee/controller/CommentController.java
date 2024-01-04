package com.leechee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.annotation.OperationLog;
import com.leechee.dto.CommentActionDTO;
import com.leechee.dto.CommentListDTO;
import com.leechee.result.CommentActionResult;
import com.leechee.result.CommentListResult;
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
    @CacheEvict(value = "commentCache", allEntries = true)
    @OperationLog(description = "添加或删除评论")
    public CommentActionResult action(CommentActionDTO commentActionDTO) {
        log.info("添加或删除评论,{}", commentActionDTO);
        CommentVO comment = commentService.action(commentActionDTO);
        return CommentActionResult.success(comment);
    }

    /**
     * 获取视频评论列表
     * @param commentListDTO
     * @return
     */
    @GetMapping("/list/")
    @Cacheable(value = "commentCache", key = "#commentListDTO.video_id + ':' + #commentListDTO.token")
    @OperationLog(description = "获取视频评论列表")
    public CommentListResult list(CommentListDTO commentListDTO) {
        log.info("获取视频评论列表,{}", commentListDTO);
        List<CommentVO> commentVOs = commentService.list(commentListDTO);
        return CommentListResult.success(commentVOs);
    }
}
