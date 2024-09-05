package com.scgecommerce.global.security.service;

import static com.scgecommerce.global.util.exception.ExceptionCode.USER_NOT_FOUND;

import com.scgecommerce.domain.member.repository.MemberRepository;
import com.scgecommerce.global.security.service.dto.CustomUserDetails;
import com.scgecommerce.global.security.service.dto.UserDetailsDto;
import com.scgecommerce.global.security.util.MyEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
  private final MyEncoder myEncoder;
  private final MemberRepository memberRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return Mono.fromSupplier(() -> {
      // getUserDetailsDomain(username) 메서드로 UserDetails를 조회합니다.
      return new CustomUserDetails(getUserDetailsDomain(username));
    });
  }

  private UserDetailsDto getUserDetailsDomain(String username) {
    return UserDetailsDto.from(memberRepository.findByEmail(myEncoder.encrypt(username))
        .orElseThrow(() -> new UsernameNotFoundException(
            USER_NOT_FOUND.getMessage()
        )));
  }
}