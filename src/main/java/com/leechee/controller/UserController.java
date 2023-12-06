package com.leechee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leechee.constant.JwtClaimsConstant;
import com.leechee.dto.UserRegisterLoginDTO;
import com.leechee.properties.JwtProperties;
import com.leechee.result.UserResult;
import com.leechee.service.UserService;
import com.leechee.utils.JwtUtil;

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
    @PostMapping("/register")
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
    @PostMapping("/login")
    public UserResult login(UserRegisterLoginDTO userRegisterLoginDTO) {
        log.info("用户登录,{}", userRegisterLoginDTO);
        Long userId = userService.login(userRegisterLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        return UserResult.success(userId, token);
    }
}
