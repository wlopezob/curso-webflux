package com.wlopezob.api_bs_v1.service.impl;

import org.springframework.stereotype.Service;

import com.wlopezob.api_bs_v1.apicaller.ApiDataCaller;
import com.wlopezob.api_bs_v1.mapper.UserMapper;
import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;
import com.wlopezob.api_bs_v1.model.dto.UserBsResponse;
import com.wlopezob.api_bs_v1.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final ApiDataCaller apiDataCaller;
  private final UserMapper userMapper;

  @Override
  public Mono<UserBsResponse> saveUser(UserBsRequest userBsRequest) {
    return Mono.just(userMapper.toUserDataRequest(userBsRequest))
        .flatMap(uDataRequest -> apiDataCaller.save(uDataRequest)
            .map(response -> userMapper.toUserBsResponse(response)));
  }
}
