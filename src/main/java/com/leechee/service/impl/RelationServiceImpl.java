package com.leechee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leechee.constant.MessageConstant;
import com.leechee.constant.StatusConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.MessageSearchDTO;
import com.leechee.dto.RelationDTO;
import com.leechee.dto.RelationSearchDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.entity.Messages;
import com.leechee.entity.Relations;
import com.leechee.entity.Users;
import com.leechee.exception.RelationException;
import com.leechee.mapper.MessageMapper;
import com.leechee.mapper.RelationMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.service.CommonService;
import com.leechee.service.RelationService;
import com.leechee.vo.FriendUserVO;
import com.leechee.vo.UserVO;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private CommonService commonService;

    /**
     * 关注与取消关注
     * @param relationDTO
     * @return
     */
    @Transactional
    @Override
    public void action(RelationDTO relationDTO) {
        Long currentId = BaseContext.getCurrentId();
        Long userId = relationDTO.getTo_user_id();
        RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
        relationSearchDTO.setFrom_user_id(currentId);
        relationSearchDTO.setTo_user_id(userId);
        List<Relations> relationsList = relationMapper.getById(relationSearchDTO);
        Relations relations = null;
        if (relationsList != null && relationsList.size() > 0)
            relations = relationsList.get(0);
        
        if (relationDTO.getAction_type().equals(StatusConstant.DO)) {
            // 关注
            if (relations == null) {
                // 插入关注数据
                relationMapper.insert(relationSearchDTO);

                // 同步更新用户关注数与粉丝数
                userMapper.updateFollowCount(currentId, 1L);
                userMapper.updateFollowerCount(userId, 1L);

            } else {
                // 重复关注
                throw new RelationException(MessageConstant.DONOT_REPEAT_ACTION);
            }

        } else if (relationDTO.getAction_type().equals(StatusConstant.CANCEL)) {
            // 取消关注
            if (relations != null) {
                // 删除关注数据
                relationMapper.delete(relations.getId());

                // 同步更新用户关注数与粉丝数
                userMapper.updateFollowCount(currentId, -1L);
                userMapper.updateFollowerCount(userId, -1L);

            } else {
                // 重复取消
                throw new RelationException(MessageConstant.DONOT_REPEAT_CANCEL);
            }

        } else {
            throw new RelationException(MessageConstant.ERROR_ACTION_TYPE);
        }
    }

    /**
     * 获取用户关注列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<UserVO> followList(UserInfoDTO userInfoDTO) {
        Long userId = userInfoDTO.getUser_id();
        Long currentId = BaseContext.getCurrentId();

        RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
        relationSearchDTO.setFrom_user_id(userId);
        List<Relations> relationList = relationMapper.getById(relationSearchDTO);
        
        List<UserVO> userVOs = new ArrayList<>();
        for (Relations relations: relationList) {
            Long toUserId = relations.getTo_user_id();
            Users usersDB = userMapper.getById(toUserId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(usersDB, userVO);

            userVO.set_follow(commonService.getRelation(currentId, toUserId));

            userVOs.add(userVO);
        }

        return userVOs;
    }

    /**
     * 获取用户粉丝列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<UserVO> followerList(UserInfoDTO userInfoDTO) {
        Long userId = userInfoDTO.getUser_id();
        Long currentId = BaseContext.getCurrentId();

        RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
        relationSearchDTO.setTo_user_id(userId);
        List<Relations> relationList = relationMapper.getById(relationSearchDTO);

        List<UserVO> userVOs = new ArrayList<>();
        for (Relations relations: relationList) {
            Long fromUserId = relations.getFrom_user_id();
            Users usersDB = userMapper.getById(fromUserId);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(usersDB, userVO);

            userVO.set_follow(commonService.getRelation(currentId, fromUserId));

            userVOs.add(userVO);
        }

        return userVOs;
    }

    /**
     * 获取用户好友列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<FriendUserVO> friendList(UserInfoDTO userInfoDTO) {
        Long currentId = BaseContext.getCurrentId();

        // 获取互相关注的用户列表
        List<Long> friendIdList = relationMapper.getCommonByCurrentId(currentId);

        List<FriendUserVO> friendUserVOs = new ArrayList<>();
        for (Long friendId: friendIdList) {

            FriendUserVO friendUserVO = new FriendUserVO();

            Users userDB = userMapper.getById(friendId);
            BeanUtils.copyProperties(userDB, friendUserVO);
            friendUserVO.set_follow(true);

            // 从message中查询fId和cId的最后一次聊天记录
            MessageSearchDTO messageSearchDTO = new MessageSearchDTO();
            messageSearchDTO.setFrom_user_id(currentId);
            messageSearchDTO.setTo_user_id(friendId);
            Messages messagesDB = messageMapper.getLatestById(messageSearchDTO);

            if (messagesDB != null) {
                friendUserVO.setMessage(messagesDB.getContent());
                if (messagesDB.getFrom_user_id().equals(currentId)) {
                    friendUserVO.setMsgType(1L);
                } else {
                    friendUserVO.setMsgType(0L);
                }
            }

            friendUserVOs.add(friendUserVO);
        }
        return friendUserVOs;
    }
    
}
