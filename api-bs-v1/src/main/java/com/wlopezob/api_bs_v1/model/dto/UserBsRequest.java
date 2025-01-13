package com.wlopezob.api_bs_v1.model.dto;

import java.util.List;

import com.wlopezob.api_bs_v1.validation.PasswordAnnotation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserBsRequest {

  @NotNull(message = "El nombre no puede ser nulo")
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String name;

  @NotNull(message = "El email no puede ser nulo")
  @NotEmpty(message = "El email no puede estar vacío")
  @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$", 
            message = "El correo debe tener un formato válido (ejemplo@dominio.com)")
  private String email;

  @NotNull(message = "La contraseña no puede ser nula")
  @NotEmpty(message = "La contraseña no puede estar vacía")
  @PasswordAnnotation(message = "El password no cumple con la política de seguridad")
  private String password;
  
  @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
  @Size(min = 1, message = "Debe de terner al menos un teléfono")
  @Valid
  private List<PhoneBsRequest> phones;
}
