package com.leechee.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.leechee.constant.MessageConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.UserInfoDTO;
import com.leechee.dto.UserRegisterLoginDTO;
import com.leechee.entity.Users;
import com.leechee.exception.UserException;
import com.leechee.mapper.UserMapper;
import com.leechee.service.CommonService;
import com.leechee.service.UserService;
import com.leechee.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonService commonService;

    @Value("${douyin.default.avatar}")
    private String avatar;
    @Value("${douyin.default.background-image}")
    private String backgroundImage;

    /**
     * 用户注册
     * @param userRegisterLoginDTO
     * @return
     */
    @Override
    public Long register(UserRegisterLoginDTO userRegisterLoginDTO) {

        String name = userRegisterLoginDTO.getUsername();
        String password = userRegisterLoginDTO.getPassword();

        // 判断用户名长度是否大于等于6位小于等于32位
        if (name.length() > 32 || name.length() < 6) {
            throw new UserException(MessageConstant.USERNAME_LENGTH_ERROR);
        }

        // 判断用户名是否存在
        // 数据库中name约束为唯一，重复会抛出数据库异常

        // 判断密码长度是否大于等于6位小于等于32位
        if (password.length() > 32 || password.length() < 6) {
            throw new UserException(MessageConstant.PASSWORD_LENGTH_ERROR);
        }

        Users users = Users.builder()
               .name(name)
               .password(DigestUtils.md5DigestAsHex(password.getBytes()))   // 密码加密
               .avatar(avatar)
               .background_image(backgroundImage)
               .build();

        userMapper.insert(users);

        return users.getId();
    }

    /**
     * 用户登录
     * @param userRegisterLoginDTO
     * @return
     */
    @Override
    public Long login(UserRegisterLoginDTO userRegisterLoginDTO) {
        String name = userRegisterLoginDTO.getUsername();
        String password = userRegisterLoginDTO.getPassword();

        // 从数据库中查找该用户名
        Users usersDB = userMapper.getByUsername(name);
        if (usersDB == null) {
            throw new UserException(MessageConstant.USER_NOT_EXIST);
        }

        // 校验密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(usersDB.getPassword())) {
            throw new UserException(MessageConstant.PASSWORD_ERROR);
        }

        return usersDB.getId();
    }

    /**
     * 获取用户信息
     * @param userInfoDTO
     * @return
     */
    @Override
    public UserVO getUserInfo(UserInfoDTO userInfoDTO) {
        Users usersDB = userMapper.getById(userInfoDTO.getUser_id());
        
        if (usersDB == null) {
            throw new UserException(MessageConstant.USER_NOT_EXIST);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(usersDB, userVO);

        // 获取关注信息
        userVO.set_follow(commonService.getRelation(BaseContext.getCurrentId(), userInfoDTO.getUser_id()));

        return userVO;
    }
}