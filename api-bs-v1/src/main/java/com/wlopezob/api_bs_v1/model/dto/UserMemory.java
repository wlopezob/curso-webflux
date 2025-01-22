package com.wlopezob.api_bs_v1.model.dto;

import com.wlopezob.api_bs_v1.thirdparty.api_data_v1.model.UserDataResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMemory {
  private UserDataResponse userDataResponse;
  private UserBsResponse userBsResponse;

}
