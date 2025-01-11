package com.wlopezob.api_data_v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class HelloController {
  @GetMapping
  public Mono<ResponseEntity<String>> hello() {
    return Mono.just(ResponseEntity.ok("Hello World"));
  }
}
