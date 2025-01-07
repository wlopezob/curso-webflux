package com.wlopezob.api_data_v1.model.dto;

import java.util.List;

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
public class UserDataRequest {
  private String name;
  private String email;
  private String password;
  private List<PhoneRequest> phones;
}
