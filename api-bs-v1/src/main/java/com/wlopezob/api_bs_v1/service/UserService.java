package com.wlopezob.api_bs_v1.service;

import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;
import com.wlopezob.api_bs_v1.model.dto.UserBsResponse;

import reactor.core.publisher.Mono;

public interface UserService {
  Mono<UserBsResponse> saveUser(UserBsRequest userBsRequest);
}
