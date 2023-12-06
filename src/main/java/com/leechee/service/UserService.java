package com.leechee.service;

import com.leechee.dto.UserInfoDTO;
import com.leechee.dto.UserRegisterLoginDTO;
import com.leechee.vo.UserVO;

public interface UserService {

    /**
     * 用户注册
     * @param userRegisterLoginDTO
     * @return
     */
    Long register(UserRegisterLoginDTO userRegisterLoginDTO);

    /**
     * 用户登录
     * @param userRegisterLoginDTO
     * @return
     */
    Long login(UserRegisterLoginDTO userRegisterLoginDTO);

    /**
     * 获取用户信息
     * @param userInfoDTO
     * @return
     */
    UserVO getUserInfo(UserInfoDTO userInfoDTO);
    
}
