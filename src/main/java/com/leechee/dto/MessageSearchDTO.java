package com.leechee.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageSearchDTO implements Serializable {

    private Long from_user_id;

    private Long to_user_id;

    private LocalDateTime create_time;
}
