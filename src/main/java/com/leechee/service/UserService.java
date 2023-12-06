package com.leechee.service;

import com.leechee.dto.UserRegisterLoginDTO;

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
    
}
