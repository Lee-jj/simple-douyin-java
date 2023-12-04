package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.VideoVO;

import lombok.Data;

@Data
public class FavoriteListResult implements Serializable{
    
    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;  

    // 用户点赞视频列表
    private List<VideoVO> video_list;

    public static FavoriteListResult success(List<VideoVO> object) {
        FavoriteListResult result = new FavoriteListResult();
        result.status_code = 0;
        result.video_list = object;
        return result;
    }

    public static FavoriteListResult error(String msg) {
        FavoriteListResult result = new FavoriteListResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }
}
