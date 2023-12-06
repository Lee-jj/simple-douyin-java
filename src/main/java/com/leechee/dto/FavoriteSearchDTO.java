package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FavoriteSearchDTO implements Serializable {
    
    private Long user_id;
    
    private Long video_id;
}
