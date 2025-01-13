package com.wlopezob.api_bs_v1.apicaller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.wlopezob.api_bs_v1.config.ApplicationProperties;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiDataCaller {

  private final WebClient.Builder webClientBuilder;
  private final ApplicationProperties applicationProperties;

  public Mono<UserDataResponse> save(UserDataRequest userDataRequest) {
    return webClientBuilder.baseUrl(applicationProperties.getBaseUrl())
      .build()
      .post()
      .uri("/user")
      .bodyValue(userDataRequest)
      .retrieve()
      .bodyToMono(UserDataResponse.class)
      .doOnNext(response -> log.info("User created: {}", response.getId()))
      .doOnError(error -> log.error("Error creating user", error))
      .doOnSuccess(response -> log.info("User created: {}", response.getId()));
    
  }
  

}
