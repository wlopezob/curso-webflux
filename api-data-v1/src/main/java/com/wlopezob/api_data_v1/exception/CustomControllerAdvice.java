package com.wlopezob.api_data_v1.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class CustomControllerAdvice {

  @ExceptionHandler(ApiException.class)
  public Mono<ResponseEntity<DataApiException>> handlerApiException(ApiException ex) {
    log.error("{}", ExceptionUtils.getStackTrace(ex));
    DataApiException dataApiException = returnDataApiException(ex);
    return Mono.just(new ResponseEntity<DataApiException>(dataApiException,
        HttpStatus.valueOf(dataApiException.getHttpStatus())));
  }

  private DataApiException returnDataApiException(
      ApiException apiException) {
    return DataApiException.builder()
        .mensaje(apiException.getMensaje())
        .httpStatus(apiException.getHttpStatus())
        .build();
  }
}
