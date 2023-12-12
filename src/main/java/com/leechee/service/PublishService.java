package com.leechee.service;

import java.util.List;

import com.leechee.dto.UserInfoDTO;
import com.leechee.vo.VideoVO;

public interface PublishService {

    /**
     * 上传视频
     * @param filePath
     * @param title
     */
    void action(String filePath, String title);

    /**
     * 获取用户发布列表
     * @param userInfoDTO
     * @return
     */
    List<VideoVO> list(UserInfoDTO userInfoDTO);
    
}
