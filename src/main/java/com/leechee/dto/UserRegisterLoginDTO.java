package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRegisterLoginDTO implements Serializable {
    
    // 注册用户名，最长32个字符
    private String username;

    // 密码，最长32个字符
    private String password;
}
