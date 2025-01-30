package com.scm.entites;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scm.enums.SingupProviderEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity  implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  // personal information
  private String name;
  private String email;
  private String phone;
  private String password;
  private String about;
  private String profilePic;

  private String emailToken;

  private String cloudinaryImagePublicId;

  // verify information
  private boolean isEnabled = false;
  private boolean isEmailVerified = false;
  private boolean isPhoneVerified = false;

  // singup Provider for user
  @Enumerated(value = EnumType.STRING)
  private SingupProviderEnum provider = SingupProviderEnum.SELF;
  private String providerId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return Collections.emptyList();
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}
