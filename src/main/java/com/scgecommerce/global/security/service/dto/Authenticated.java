package com.scgecommerce.global.security.service.dto;


import com.scgecommerce.domain.member.type.Role;

public interface Authenticated {
  Long getId();
  String getUsername();
  String getPassword();
  Role getRole();
}
