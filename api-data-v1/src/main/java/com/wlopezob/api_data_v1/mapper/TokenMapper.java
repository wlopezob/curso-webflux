package com.wlopezob.api_data_v1.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.wlopezob.api_data_v1.model.entity.TokenEntity;

@Component
public class TokenMapper {
    
    public TokenEntity toTokenEntity(String token, Long userId) {
        return TokenEntity.builder()
            .token(token)
            .userId(userId)
            .active(true)
            .created(LocalDateTime.now())
            .build();
    }
}
