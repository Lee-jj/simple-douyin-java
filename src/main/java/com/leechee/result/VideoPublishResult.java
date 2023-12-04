package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.VideoVO;

import lombok.Data;

@Data
public class VideoPublishResult implements Serializable {
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;   

    // 用户发布的视频列表
    private List<VideoVO> video_list;

    public static VideoPublishResult success(List<VideoVO> video_list) {
        VideoPublishResult result = new VideoPublishResult();
        result.status_code = 0;
        result.video_list = video_list;
        return result;
    }

    public static VideoPublishResult error(String msg) {
        VideoPublishResult result = new VideoPublishResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
