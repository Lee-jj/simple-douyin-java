package com.leechee.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.leechee.dto.DeleteVideoDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.vo.VideoVO;

public interface PublishService {

    /**
     * 上传视频
     * @param filePath
     * @param title
     */
    void action(String filePath, String coverPath, String title);

    /**
     * 获取用户发布列表
     * @param userInfoDTO
     * @return
     */
    List<VideoVO> list(UserInfoDTO userInfoDTO);

    /**
     * 根据视频文件获得封面文件
     * @param file
     * @return
     */
    MultipartFile getCoverFile(MultipartFile file);

    /**
     * 删除视频
     * @param deleteVideoDTO
     * @return
     */
    void deleteVideo(DeleteVideoDTO deleteVideoDTO);
    
}
