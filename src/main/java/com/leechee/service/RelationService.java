package com.leechee.service;

import com.leechee.dto.RelationDTO;

public interface RelationService {

    /**
     * 关注与取消关注
     * @param relationDTO
     * @return
     */
    void action(RelationDTO relationDTO);
    
}
