package com.wlopezob.api_bs_v1.service;

import reactor.core.publisher.Mono;

public interface RedisService {
  <T> Mono<T> save(String key, T value);

  <T> Mono<T> get(String key, Class<T> type);
}
