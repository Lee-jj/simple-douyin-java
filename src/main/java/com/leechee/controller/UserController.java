package com.leechee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.annotation.OperationLog;
import com.leechee.constant.JwtClaimsConstant;
import com.leechee.dto.UserInfoDTO;
import com.leechee.dto.UserRegisterLoginDTO;
import com.leechee.properties.JwtProperties;
import com.leechee.result.UserInfoResult;
import com.leechee.result.UserResult;
import com.leechee.service.UserService;
import com.leechee.utils.JwtUtil;
import com.leechee.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/douyin/user")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户注册
     * @param userRegisterLoginDTO
     * @return
     */
    @PostMapping("/register/")
    @OperationLog(description = "用户注册")
    public UserResult register(UserRegisterLoginDTO userRegisterLoginDTO) {
        log.info("用户注册,{}", userRegisterLoginDTO);
        Long userId = userService.register(userRegisterLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        return UserResult.success(userId, token);
    }

    /**
     * 用户登录
     * @param userRegisterLoginDTO
     * @return
     */
    @PostMapping("/login/")
    @OperationLog(description = "用户登录")
    public UserResult login(UserRegisterLoginDTO userRegisterLoginDTO) {
        log.info("用户登录,{}", userRegisterLoginDTO);
        Long userId = userService.login(userRegisterLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        return UserResult.success(userId, token);
    }

    /**
     * 获取用户信息
     * @param userInfoDTO
     * @return
     */
    @GetMapping("/")
    @OperationLog(description = "获取用户信息")
    public UserInfoResult getUserInfo(UserInfoDTO userInfoDTO) {
        log.info("获取用户信息,{}", userInfoDTO);
        UserVO userVO = userService.getUserInfo(userInfoDTO);
        return UserInfoResult.success(userVO);
    }
}
