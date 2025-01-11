package com.wlopezob.api_data_v1.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.wlopezob.api_data_v1.model.dto.UserDataRequest;
import com.wlopezob.api_data_v1.model.dto.UserDataResponse;
import com.wlopezob.api_data_v1.model.entity.UserEntity;

@Component
public class UserMapper {

  public UserEntity toUserEntity(UserDataRequest userDataRequest) {
    return UserEntity.builder()
        .name(userDataRequest.getName())  // Fix: changed from email to name
        .email(userDataRequest.getEmail())
        .password(userDataRequest.getPassword())
        .active(true)
        .created(LocalDate.now())
        .modified(LocalDate.now())
        .lastLogin(LocalDate.now())
        .build();
  }

  public UserDataResponse toUserDataResponse(UserEntity usu, String token) {
    return UserDataResponse.builder()
        .id(usu.getUserId().toString())
        .created(usu.getCreated().toString())
        .modified(usu.getModified().toString())
        .lastLogin(usu.getLastLogin().toString())
        .isActive(usu.isActive())
        .token(token)
        .build();
  }

}
