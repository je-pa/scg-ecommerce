package com.scgecommerce.global.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scgecommerce.global.util.exception.CustomException;
import com.scgecommerce.global.util.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Component
public class ExceptionHandlingFilter implements WebFilter {
  private final ObjectMapper objectMapper;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    try {
      return chain.filter(exchange);
    } catch (CustomException e) {
      log.error(e.getMessage());
      ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getStatusCode(), e.getMessage());
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(e.getStatusCode());
      response.getHeaders().setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
      byte[] bytes = null;
      try {
        bytes = objectMapper.writeValueAsBytes(exceptionResponse);
      } catch (JsonProcessingException ex) {
        throw new RuntimeException(ex);
      }
      return response.writeWith(
          Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
  }
}
