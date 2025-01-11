package com.wlopezob.api_data_v1.mapper;

import org.springframework.stereotype.Component;

import com.wlopezob.api_data_v1.model.dto.PhoneRequest;
import com.wlopezob.api_data_v1.model.entity.PhoneEntity;

@Component
public class PhoneMapper {
    
    public PhoneEntity toPhoneEntity(PhoneRequest phoneRequest, Long userId) {
        return PhoneEntity.builder()
            .number(phoneRequest.getNumber())
            .cityCode(phoneRequest.getCityCode())
            .countryCode(phoneRequest.getContryCode())
            .userId(userId)
            .build();
    }
}
