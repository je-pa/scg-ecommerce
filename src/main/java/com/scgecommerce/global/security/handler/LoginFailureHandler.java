package com.scgecommerce.global.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scgecommerce.global.util.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange,
      AuthenticationException exception) {
    log.info("로그인 실패");

    ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    response.setStatusCode(HttpStatus.BAD_REQUEST);

    ExceptionResponse exceptionResponse = ExceptionResponse.of(HttpStatus.UNAUTHORIZED, exception);

    DataBuffer dataBuffer;
    try {
      dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(exceptionResponse));
    } catch (JsonProcessingException e) {
      return Mono.error(e);
    }

    return response.writeWith(Mono.just(dataBuffer));
  }
}
