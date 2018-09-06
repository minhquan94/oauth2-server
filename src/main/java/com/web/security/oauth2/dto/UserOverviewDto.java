/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserOverviewDto.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class UserOverviewDto implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2023379587030489248L;

  /** The username. */
  private String username;

  /** The user dtos. */
  private List<UserDto> userDtos = new ArrayList<>();

  /**
   * Instantiates a new user overview dto.
   */
  public UserOverviewDto() {
  }

  /**
   * Gets the size.
   *
   * @return the size
   */
  public int getSize() {
    return userDtos.size();
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username
   *          the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the user dtos.
   *
   * @return the user dtos
   */
  public List<UserDto> getUserDtos() {
    return userDtos;
  }

  /**
   * Sets the user dtos.
   *
   * @param userDtos
   *          the new user dtos
   */
  public void setUserDtos(List<UserDto> userDtos) {
    this.userDtos = userDtos;
  }
}
