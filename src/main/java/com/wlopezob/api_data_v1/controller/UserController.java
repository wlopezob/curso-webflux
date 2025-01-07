package com.wlopezob.api_data_v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopezob.api_data_v1.model.dto.UserDataRequest;
import com.wlopezob.api_data_v1.model.dto.UserDataResponse;
import com.wlopezob.api_data_v1.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public Mono<ResponseEntity<UserDataResponse>> save(@RequestBody UserDataRequest userDataRequest) {
    return userService.save(userDataRequest)
      .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user));
  }

}
