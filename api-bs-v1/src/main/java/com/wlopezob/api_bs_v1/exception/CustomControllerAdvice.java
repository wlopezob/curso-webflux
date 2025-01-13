package com.wlopezob.api_bs_v1.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<DataApiException>> handlerApiException(Exception ex) {
    String stackTrace = ExceptionUtils.getStackTrace(ex);
    log.error("{}", stackTrace);
    DataApiException dataApiException = DataApiException.builder()
        .mensaje(String.format("%s, %s", ex.getMessage(), stackTrace))
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();
    return Mono.just(new ResponseEntity<DataApiException>(dataApiException,
        HttpStatus.valueOf(dataApiException.getHttpStatus())));
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<DataApiException>> handleValidationExceptions(WebExchangeBindException ex) {

    var cadena = Mono.just("mi cadena")
        .doOnSuccess(log::info);
    
    return cadena.flatMap(cad -> Flux.fromIterable(ex.getBindingResult().getAllErrors())
        .map(error -> error.getDefaultMessage())
        .collectList()
        .map(errors -> DataApiException.builder()
            .mensaje(String.join(", ", errors))
            .httpStatus(HttpStatus.BAD_REQUEST.value())
            .build())
        .map(response -> ResponseEntity.badRequest().body(response)));

    // List<String> errors = ex.getBindingResult().getAllErrors().stream()
    // .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
    // DataApiException dataApiException = DataApiException.builder()
    // .mensaje(String.join(", ", errors))
    // .httpStatus(HttpStatus.BAD_REQUEST.value())
    // .build();
    // return Mono.just(ResponseEntity.badRequest().body(dataApiException));
  }
}
