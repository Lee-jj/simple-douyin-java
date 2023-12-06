package com.leechee.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelationSearchDTO implements Serializable{
    
    private Long from_user_id;

    private Long to_user_id;
}
