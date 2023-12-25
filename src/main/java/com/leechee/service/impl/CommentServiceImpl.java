package com.leechee.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.CommentActionDTO;
import com.leechee.dto.CommentListDTO;
import com.leechee.entity.Comments;
import com.leechee.entity.Users;
import com.leechee.exception.CommentException;
import com.leechee.mapper.CommentMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.CommentService;
import com.leechee.service.CommonService;
import com.leechee.vo.CommentVO;
import com.leechee.vo.UserVO;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CommonService commonService;

    /**
     * 添加或删除评论
     * @param commentActionDTO
     * @return
     */
    @Transactional
    @Override
    public CommentVO action(CommentActionDTO commentActionDTO) {

        Long currentId = BaseContext.getCurrentId();
        Long videoId = commentActionDTO.getVideo_id();
        
        if (commentActionDTO.getAction_type().equals(StatusConstant.DO)) {
            // 发布评论返回评论内容
            Comments comments = Comments.builder()
                   .content(commentActionDTO.getComment_text())
                   .create_date(LocalDateTime.now())
                   .user_id(currentId)
                   .video_id(videoId)
                   .build();

            commentMapper.insert(comments);

            // 同步更新视频的评论总数
            videoMapper.updateCommentCount(videoId, 1);

            CommentVO commentVO = new CommentVO();
            Users users = userMapper.getById(currentId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(users, userVO);
            commentVO.setUser(userVO);
            commentVO.setContent(commentActionDTO.getComment_text());
            commentVO.setCreate_date(LocalDateTime.now());
            return commentVO;
            
        } else if (commentActionDTO.getAction_type().equals(StatusConstant.CANCEL)) {
            // 删除评论返回null
            // 判断评论是否存在
            Long comment_id = commentActionDTO.getComment_id();
            Comments commentsDB = commentMapper.getById(comment_id);
            if (commentsDB == null) {
                throw new CommentException(MessageConstant.COMMENT_NOT_EXIST);
            }
            commentMapper.delete(comment_id);

            // 同步更新视频的评论总数
            videoMapper.updateCommentCount(videoId, -1);

            return null;

        } else {
            throw new CommentException(MessageConstant.ERROR_ACTION_TYPE);
        }
    }

    /**
     * 获取视频评论列表
     * @param commentListDTO
     * @return
     */
    @Override
    public List<CommentVO> list(CommentListDTO commentListDTO) {
        Long video_id = commentListDTO.getVideo_id();
        List<Comments> commentsList = commentMapper.getByVideoId(video_id);

        List<CommentVO> commentVOs = new ArrayList<>();
        for (Comments comments: commentsList) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comments, commentVO);

            Long userId = comments.getUser_id();
            Users usersDB = userMapper.getById(userId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(usersDB, userVO);

            // 关注信息设置
            Long currentId = BaseContext.getCurrentId();
            userVO.set_follow(commonService.getRelation(currentId, userId));

            commentVO.setUser(userVO);
            commentVOs.add(commentVO);
        }

        return commentVOs;
    }
    
}
