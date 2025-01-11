package com.wlopezob.api_data_v1.service.impl;

import org.springframework.stereotype.Service;

import com.wlopezob.api_data_v1.exception.ApiExceptionEnum;
import com.wlopezob.api_data_v1.mapper.PhoneMapper;
import com.wlopezob.api_data_v1.mapper.TokenMapper;
import com.wlopezob.api_data_v1.mapper.UserMapper;
import com.wlopezob.api_data_v1.model.dto.UserDataRequest;
import com.wlopezob.api_data_v1.model.dto.UserDataResponse;
import com.wlopezob.api_data_v1.repository.PhoneRepository;
import com.wlopezob.api_data_v1.repository.TokenRepository;
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
  private final TokenRepository tokenRepository;
  private final UserMapper userMapper;
  private final PhoneMapper phoneMapper;
  private final TokenMapper tokenMapper;

  private Mono<Void> validateEmail(String email) {
    return userRepository.findByEmailAndActive(email, true)
        .flatMap(existingUser -> Mono.error(ApiExceptionEnum.ER0001.buildException()))
        .then();
  }

  @Override
  public Mono<UserDataResponse> save(UserDataRequest userDataRequest) {
    return validateEmail(userDataRequest.getEmail())
        .then(Mono.just(userMapper.toUserEntity(userDataRequest)))
        .flatMap(userRepository::save)
        .flatMap(savedUser -> Flux.fromIterable(userDataRequest.getPhones())
            .map(phone -> phoneMapper.toPhoneEntity(phone, savedUser.getUserId()))
            .collectList()
            .flatMap(phones -> phoneRepository.saveAll(phones)
                .then(Mono.just(savedUser)))
            .map(user -> tokenMapper.toTokenEntity(userDataRequest.getToken(), savedUser.getUserId()))
            .flatMap(tokenRepository::save)
            .then(Mono.just(savedUser)))
        .map(usu -> userMapper.toUserDataResponse(usu, userDataRequest.getToken()));
  }

}
