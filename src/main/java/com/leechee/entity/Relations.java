package com.leechee.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relations implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long from_user_id;

    private Long to_user_id;
}
