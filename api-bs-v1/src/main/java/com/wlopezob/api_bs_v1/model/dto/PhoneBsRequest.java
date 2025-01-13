package com.wlopezob.api_bs_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class PhoneBsRequest {
  
  @NotNull(message = "El número no puede ser nulo")
  @NotEmpty(message = "El número no puede estar vacío")
  private String number;


  @NotNull(message = "El código de ciudad no puede ser nulo")
  @NotEmpty(message = "El código de ciudad no puede estar vacío")
  @JsonProperty("citycode")
  private String cityCode;

  @NotNull(message = "El código de país no puede ser nulo")
  @NotEmpty(message = "El código de país no puede estar vacío")
  @JsonProperty("contrycode")
  private String contryCode;
}
