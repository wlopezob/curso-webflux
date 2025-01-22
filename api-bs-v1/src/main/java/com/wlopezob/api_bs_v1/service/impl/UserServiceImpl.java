package com.wlopezob.api_bs_v1.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlopezob.api_bs_v1.apicaller.ApiDataCaller;
import com.wlopezob.api_bs_v1.config.JwtToken;
import com.wlopezob.api_bs_v1.mapper.UserMapper;
import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;
import com.wlopezob.api_bs_v1.model.dto.UserBsResponse;
import com.wlopezob.api_bs_v1.model.dto.UserMemory;
import com.wlopezob.api_bs_v1.service.RedisService;
import com.wlopezob.api_bs_v1.service.UserService;
import com.wlopezob.api_bs_v1.util.Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final ApiDataCaller apiDataCaller;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtToken jwtToken;
  private final RedisService redisService;

  @Override
  public Mono<UserBsResponse> saveUser(UserBsRequest userBsRequest) {
    userBsRequest.setPassword(passwordEncoder.encode(userBsRequest.getPassword()));
    String token = jwtToken.generateToken(userBsRequest);
    return Mono.just(userMapper.toUserDataRequest(userBsRequest, token))
        .flatMap(uDataRequest -> apiDataCaller.save(uDataRequest)
            .map(UserDataResponse -> UserMemory.builder()
                .userDataResponse(UserDataResponse)
                .userBsResponse(userMapper.toUserBsResponse(UserDataResponse))
                .build())
            .flatMap(userMemory -> {
              String uuid = UUID.randomUUID().toString();
              userMemory.getUserBsResponse().setId(uuid);
              return redisService.save(Util.getKeyRedis(uuid), userMemory.getUserDataResponse())
                  .map(rs1 -> userMemory.getUserBsResponse());
            }));

  }
}
