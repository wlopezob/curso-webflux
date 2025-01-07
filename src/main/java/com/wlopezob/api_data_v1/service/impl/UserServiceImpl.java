package com.wlopezob.api_data_v1.service.impl;

import org.springframework.stereotype.Service;

import com.wlopezob.api_data_v1.mapper.PhoneMapper;
import com.wlopezob.api_data_v1.mapper.UserMapper;
import com.wlopezob.api_data_v1.model.dto.UserDataRequest;
import com.wlopezob.api_data_v1.model.dto.UserDataResponse;
import com.wlopezob.api_data_v1.repository.PhoneRepository;
import com.wlopezob.api_data_v1.repository.UserRepository;
import com.wlopezob.api_data_v1.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PhoneRepository phoneRepository;
  private final UserMapper userMapper;
  private final PhoneMapper phoneMapper;

  @Override
  public Mono<UserDataResponse> save(UserDataRequest userDataRequest) {
    return Mono.just(userMapper.toUserEntity(userDataRequest))
        .flatMap(userRepository::save)
        .flatMap(savedUser -> Flux.fromIterable(userDataRequest.getPhones())
            .map(phone -> phoneMapper.toPhoneEntity(phone, savedUser.getUserId()))
            .flatMap(phoneRepository::save)
            .then(Mono.just(savedUser)))
        .map(userMapper::toUserDataResponse);
  }

}
