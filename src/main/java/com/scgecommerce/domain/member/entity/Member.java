package com.scgecommerce.domain.member.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.scgecommerce.domain.BaseEntity;
import com.scgecommerce.domain.member.type.Role;
import com.scgecommerce.global.security.service.dto.Authenticated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements Authenticated {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "email", unique = true, nullable = false, length = 50)
  private String email;

  @Column(name = "password", nullable = false, length = 60)
  private String password;

  @Column(name = "tell_number", nullable = false, length = 50)
  private String tellNumber;

  @Column(name = "address", nullable = false)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  @Builder
  public Member(String address, String email, String name, String password,
      String tellNumber, Role role) {
    this.address = address;
    this.email = email;
    this.name = name;
    this.password = password;
    this.tellNumber = tellNumber;
    this.role = role;
  }

  @Override
  public String getUsername() {
    return this.email;
  }
}
