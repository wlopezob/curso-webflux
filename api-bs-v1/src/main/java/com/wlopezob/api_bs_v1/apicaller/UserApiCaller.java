package com.wlopezob.api_bs_v1.apicaller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;

import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.api.UserApi;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataRequest;
import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

public class UserApiCaller implements UserApi{

  @Override
  public Mono<ResponseEntity<UserDataResponse>> save(@Valid Mono<UserDataRequest> userDataRequest,
      ServerWebExchange exchange) {
        
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

}
