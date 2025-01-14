package com.wlopezob.api_bs_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wlopezob.api_bs_v1.exception.ApiException;
import com.wlopezob.api_bs_v1.exception.DataApiException;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .filter(errorHandlingFilter())
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
            .build());
  }

  private ExchangeFilterFunction errorHandlingFilter() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      if (clientResponse.statusCode().isError()) {
        return clientResponse.bodyToMono(DataApiException.class)
            .flatMap(dataError -> Mono.<ClientResponse>error(
                new ApiException(dataError.getMensaje(),
                    clientResponse.statusCode().value(),
                    null)))
            .onErrorResume(WebClientResponseException.class, e -> Mono.<ClientResponse>error(new ApiException(
                e.getMessage(),
                clientResponse.statusCode().value(),
                e)));

      }
      return Mono.just(clientResponse);
    });
  }
}
