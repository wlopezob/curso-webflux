package com.wlopezob.api_data_v1.service;

import com.wlopezob.api_data_v1.model.dto.UserDataRequest;
import com.wlopezob.api_data_v1.model.dto.UserDataResponse;

import reactor.core.publisher.Mono;

public interface UserService {
  Mono<UserDataResponse> save(UserDataRequest userDataRequest);
}
