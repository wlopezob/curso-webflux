package com.wlopezob.api_data_v1.model.dto;

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
public class UserDataResponse {
  private String id;
  private String created;
  private String modified;
  private String lastLogin;
  private String token;
  private boolean isActive;
}
