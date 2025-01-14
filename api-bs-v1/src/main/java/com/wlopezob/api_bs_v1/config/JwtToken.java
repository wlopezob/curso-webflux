package com.wlopezob.api_bs_v1.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.wlopezob.api_bs_v1.model.dto.UserBsRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtToken {
  @Value("${security.jwt.secret}")
  private String secret;
  @Value("${security.jwt.expiration}")
  private long expiration;
  @Value("${security.jwt.rol}")
  private String rol;

    public String generateToken(UserBsRequest userRequest) {
    List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(rol.toUpperCase()));
    return Jwts.builder()
      .subject(userRequest.getEmail())
      .claim("authorities", roles)
      .issuedAt(new java.util.Date(System.currentTimeMillis()))
      .expiration(new java.util.Date(System.currentTimeMillis() + expiration))
      .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret)))     
      .compact();
  }
}
