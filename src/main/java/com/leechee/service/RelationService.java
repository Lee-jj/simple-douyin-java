package com.leechee.service;

import java.util.List;

import com.leechee.dto.RelationDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.vo.FriendUserVO;
import com.leechee.vo.UserVO;

public interface RelationService {

    /**
     * 关注与取消关注
     * @param relationDTO
     * @return
     */
    void action(RelationDTO relationDTO);

    /**
     * 获取用户关注列表
     * @param userInfoDTO
     * @return
     */
    List<UserVO> followList(UserInfoDTO userInfoDTO);

    /**
     * 获取用户粉丝列表
     * @param userInfoDTO
     * @return
     */
    List<UserVO> followerList(UserInfoDTO userInfoDTO);

    /**
     * 获取用户好友列表
     * @param userInfoDTO
     * @return
     */
    List<FriendUserVO> friendList(UserInfoDTO userInfoDTO);
    
}
