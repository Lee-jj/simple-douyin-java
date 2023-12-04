package com.leechee.result;

import java.io.Serializable;
import java.util.List;

import com.leechee.vo.VideoVO;

import lombok.Data;

@Data
public class FeedResult implements Serializable{

    // 编码：0成功，其它数字为失败
    private Integer status_code;    

    // 返回状态描述
    private String status_msg;      

    // 本次返回的视频中，发布最早的时间，作为下次请求时的latest_time
    private Long next_time;

    // 视频列表
    private List<VideoVO> video_list;

    public static FeedResult success(List<VideoVO> object, Long time) {
        FeedResult result = new FeedResult();
        result.status_code = 0;
        result.video_list = object;
        result.next_time = time;
        return result;
    }

    public static FeedResult error(String msg) {
        FeedResult result = new FeedResult();
        result.status_code = 1;
        result.status_msg = msg;
        return result;
    }

}
