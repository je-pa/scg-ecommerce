package com.scgecommerce.global.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements ServerLogoutSuccessHandler {

  @Override
  public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
    log.info("로그아웃 성공");
    ServerHttpResponse response = exchange.getExchange().getResponse();
    response.setStatusCode(HttpStatus.OK);

    return response.setComplete();
  }
}
