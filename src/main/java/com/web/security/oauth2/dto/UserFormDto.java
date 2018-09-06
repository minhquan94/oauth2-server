/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.dto;

import com.web.security.oauth2.domain.Privilege;
import com.web.security.oauth2.domain.entity.UserEntity;
import com.web.security.oauth2.utils.PasswordCodecUtils;

/**
 * The Class UserFormDto.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class UserFormDto extends UserDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7959857016962260738L;

  /** The password. */
  private String password;

  /**
   * Instantiates a new user form dto.
   */
  public UserFormDto() {
    // do nothing
  }

  /**
   * Gets the all privileges.
   *
   * @return the all privileges
   */
  public Privilege[] getAllPrivileges() {
    return new Privilege[]{Privilege.MOBILE, Privilege.UNITY };
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password
   *          the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * New user.
   *
   * @return the user
   */
  public UserEntity newUser() {
    final UserEntity user = new UserEntity();
    user.setUsername(getUsername());
    user.setPassword(PasswordCodecUtils.encode(getPassword()));
    user.setPhone(getPhone());
    user.setEmail(getEmail());
    return user;
  }
}
