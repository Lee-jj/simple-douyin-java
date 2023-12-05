package com.leechee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.leechee.constant.MessageConstant;
import com.leechee.dto.UserRegisterLoginDTO;
import com.leechee.entity.Users;
import com.leechee.exception.UserException;
import com.leechee.mapper.UserMapper;
import com.leechee.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param userRegisterLoginDTO
     * @return
     */
    @Override
    public Long register(UserRegisterLoginDTO userRegisterLoginDTO) {

        String name = userRegisterLoginDTO.getUsername();
        String password = userRegisterLoginDTO.getPassword();

        // 判断用户名长度是否小于等于32位
        if (name.length() > 32) {
            throw new UserException(MessageConstant.USERNAME_TO_LONG);
        }

        // 判断用户名是否存在
        // 数据库中name约束为唯一，重复会抛出数据库异常

        // 判断密码长度是否小于等于32位
        if (password.length() > 32) {
            throw new UserException(MessageConstant.PASSWORD_TO_LONG);
        }

        // TODO OSS默认图片链接
        String avatar = "default avatar link";
        String background_image = "default background image link";

        Users users = Users.builder()
               .name(name)
               .password(DigestUtils.md5DigestAsHex(password.getBytes()))   // 密码加密
               .avatar(avatar)
               .background_image(background_image)
               .build();

        userMapper.insert(users);

        return users.getId();
    }
    
}
