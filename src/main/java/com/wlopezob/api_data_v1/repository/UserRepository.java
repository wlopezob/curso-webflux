package com.wlopezob.api_data_v1.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.wlopezob.api_data_v1.model.entity.UserEntity;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

  
} 
