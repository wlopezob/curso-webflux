package com.wlopezob.api_bs_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
 @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    return http.csrf(csrf -> csrf.disable())
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers(HttpMethod.POST, "/user").permitAll()
            .pathMatchers(HttpMethod.GET, "/user/getkey/{key}").permitAll()
            .pathMatchers("/openapi").permitAll()
            .anyExchange().authenticated()
        )
        .httpBasic(hb -> hb.disable())
        .formLogin(fl -> fl.disable())
        .build();
  }
}
