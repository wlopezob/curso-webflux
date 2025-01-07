package com.wlopezob.api_data_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class PhoneRequest {
  private String number;

  @JsonProperty("citycode")
  private String cityCode;

  @JsonProperty("contrycode")
  private String contryCode;
}
