package com.scgecommerce.global.config;

import com.scgecommerce.global.security.handler.CustomLogoutSuccessHandler;
import com.scgecommerce.global.security.handler.ExceptionHandlingFilter;
import com.scgecommerce.global.security.handler.LoginFailureHandler;
import com.scgecommerce.global.security.handler.LoginSuccessHandler;
import com.scgecommerce.global.security.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ExceptionHandlingFilter exceptionHandlingFilter;
  private final JwtAuthenticationFilter authenticationFilter;
  private final LoginFailureHandler loginFailureHandler;
  private final LoginSuccessHandler loginSuccessHandler;
  private final ReactiveUserDetailsService userDetailsService;
  private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
    http
        .formLogin(form -> form
            .loginPage("/api/auth/login")
            .authenticationManager(authenticationManager())
//            .authe(new CustomFormLoginAuthenticationConverter()) // 커스텀 로그인 필터 적용
            .authenticationSuccessHandler(loginSuccessHandler)
            .authenticationFailureHandler(loginFailureHandler))
        .logout(
            logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
        )
        .httpBasic(basicSpec -> basicSpec.disable())
        .csrf(csrf -> csrf.disable())
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // 세션을 비활성화
        .authorizeExchange(authorize -> authorize
            .pathMatchers("/api/auth/login","/api/v0/auth/signup","/api/v0/auth/test/post",
                "/api/v0/auth/send-email-auth-code",
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .pathMatchers(HttpMethod.GET, "/api/v0/products", "/api/v0/products/**").permitAll()
            .anyExchange().authenticated()
        )

        .addFilterAt(this.authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .addFilterAt(this.exceptionHandlingFilter, SecurityWebFiltersOrder.FORM_LOGIN);

    return http.build();
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
        new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder());
    return authenticationManager;
  }
}
