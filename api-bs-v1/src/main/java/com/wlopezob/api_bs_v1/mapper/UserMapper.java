package com.wlopezob.api_bs_v1.mapper;

import org.springframework.stereotype.Component;

import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;
import com.wlopezob.api_bs_v1.model.dto.UserBsResponse;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.PhoneRequest;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataResponse;

@Component
public class UserMapper {

  public UserDataRequest toUserDataRequest(UserBsRequest userBsRequest, String token) {
    return new UserDataRequest()
        .name(userBsRequest.getName())
        .email(userBsRequest.getEmail())
        .password(userBsRequest.getPassword())
        .token(token)
        .phones(userBsRequest.getPhones()
            .stream()
            .map(phone -> new PhoneRequest()
                .number(phone.getNumber())
                .citycode(phone.getCityCode())
                .contrycode(phone.getContryCode()))
            .toList());
  }

  public UserBsResponse toUserBsResponse(UserDataResponse response) {
    return UserBsResponse.builder()
        .id(response.getId())
        .created(response.getCreated())
        .modified(response.getModified())
        .lastLogin(response.getLastLogin())
        .token(response.getToken())
        .isActive(response.getActive())
        .build();
  }
}
