package com.wlopezob.api_bs_v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;
import com.wlopezob.api_bs_v1.model.dto.UserBsResponse;
import com.wlopezob.api_bs_v1.service.RedisService;
import com.wlopezob.api_bs_v1.service.UserService;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final RedisService redisService;

  @PostMapping
  public Mono<ResponseEntity<UserBsResponse>> save(@RequestBody @Valid UserBsRequest userBsRequest) {
    return userService.saveUser(userBsRequest)
        .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user));
  }

  @GetMapping("/getkey/{key}")
  public Mono<ResponseEntity<UserDataResponse>> getKey(@PathVariable("key") String key) {
    return redisService.get(key, UserDataResponse.class)
        .map(value -> ResponseEntity.ok(value))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
