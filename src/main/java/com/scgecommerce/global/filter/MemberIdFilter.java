package com.scgecommerce.global.filter;

import com.scgecommerce.global.security.service.dto.CustomUserDetails;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MemberIdFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();

    return context
        .flatMap(securityContext -> {
          Authentication authentication = securityContext.getAuthentication();
          Object principal = authentication.getPrincipal();
          Long memberId = null;

          if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            memberId = userDetails.getMemberId();
          }

          if (memberId != null) {
            exchange.getRequest().mutate()
                .header("X-Member-Id", String.valueOf(memberId));
          }

          return chain.filter(exchange);
        })
        .switchIfEmpty(chain.filter(exchange));
  }
}

