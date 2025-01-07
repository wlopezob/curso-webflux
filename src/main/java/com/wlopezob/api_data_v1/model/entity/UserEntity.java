package com.wlopezob.api_data_v1.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    @Id
    private Long userId;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate lastLogin;
    
}