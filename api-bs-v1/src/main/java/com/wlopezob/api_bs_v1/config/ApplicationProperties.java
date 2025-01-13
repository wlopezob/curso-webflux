package com.wlopezob.api_bs_v1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "http-client.api-data-v1")
@Getter
@Setter
@Component
public class ApplicationProperties {
  private String baseUrl;
  private int timeout;
}
