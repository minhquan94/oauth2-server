/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web.security.oauth2.domain.entity.UserEntity;
import com.web.security.oauth2.domain.entity.UserPrivilegeEntity;

/**
 * The Class CustomUserDetails.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class CustomUserDetails implements UserDetails {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3957586021470480642L;

  /** The Constant ROLE_PREFIX. */
  private static final String ROLE_PREFIX = "ROLE_";

  /** The Constant DEFAULT_USER_ROLE. */
  private static final GrantedAuthority DEFAULT_USER_ROLE =
      new SimpleGrantedAuthority(ROLE_PREFIX + Privilege.USER.name());

  /** The user. */
  private UserEntity user;

  /** The granted authorities. */
  private final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

  /**
   * Instantiates a new SOS user details.
   */
  public CustomUserDetails() {
    // do nothing
  }

  /**
   * Instantiates a new SOS user details.
   *
   * @param user
   *          the user
   */
  public CustomUserDetails(UserEntity user) {
    this.user = user;
    initialAuthorities();
  }

  /**
   * Initial authorities.
   */
  private void initialAuthorities() {
    // Default, everyone have it
    this.grantedAuthorities.add(DEFAULT_USER_ROLE);
    final List<UserPrivilegeEntity> privileges = user.getPrivileges();
    for (final UserPrivilegeEntity privilege : privileges) {
      this.grantedAuthorities
          .add(new SimpleGrantedAuthority(ROLE_PREFIX + privilege.getPrivilege().name()));
    }
  }

  /**
   * Return authorities, more information see {@link #initialAuthorities()}.
   *
   * @return Collection of GrantedAuthority
   */
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return this.grantedAuthorities;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
   */
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
   */
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

  /**
   * User.
   *
   * @return the user
   */
  public UserEntity user() {
    return user;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("{user=").append(user);
    sb.append('}');
    return sb.toString();
  }
}
