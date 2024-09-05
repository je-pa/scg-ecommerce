package com.scgecommerce.global.security.handler;

import com.scgecommerce.global.security.jwt.util.TokenProvider;
import com.scgecommerce.global.security.service.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;

  @Override
  public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
      Authentication authentication) {
    log.info("로그인 성공");

    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

    response.getHeaders().set(HttpHeaders.AUTHORIZATION,
        tokenProvider.generateToken(
            principal.getMemberId(), principal.getUsername(), principal.getAuthorities()));
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    response.setStatusCode(HttpStatus.OK);

    return response.setComplete();
  }
}
