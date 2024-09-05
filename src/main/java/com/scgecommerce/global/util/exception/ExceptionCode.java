package com.scgecommerce.global.util.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

  // Unauthorized:401:인증이슈
  INVALID_JWT_SIGNATURE(UNAUTHORIZED, "유효하지 않는 JWT 서명입니다."),
  EXPIRED_JWT_TOKEN(UNAUTHORIZED, "만료된 JWT token 입니다."),
  UNSUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
  JWT_CLAIMS_EMPTY(UNAUTHORIZED, "잘못된 JWT 토큰입니다."),


  // NOT_FOUND:404:자원없음
  USER_NOT_FOUND(NOT_FOUND, "유저 개체를 찾지 못했습니다."),

  // INTERNAL_SERVER_ERROR:500:서버 문제 발생
  NO_SUCH_ALGORITHM(INTERNAL_SERVER_ERROR, "지정된 알고리즘을 찾을 수 없습니다."),
  NO_SUCH_PADDING(INTERNAL_SERVER_ERROR, "지정된 패딩 방식을 찾을 수 없습니다."),
  INVALID_KEY(INTERNAL_SERVER_ERROR, "잘못된 키입니다."),
  ILLEGAL_BLOCK_SIZE(INTERNAL_SERVER_ERROR, "잘못된 블록 크기입니다."),
  BAD_PADDING(INTERNAL_SERVER_ERROR, "잘못된 패딩입니다."),
  ;
  private final HttpStatus status;
  private final String message;

}