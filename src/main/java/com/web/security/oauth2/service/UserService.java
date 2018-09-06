/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.web.security.oauth2.dto.UserFormDto;
import com.web.security.oauth2.dto.UserJsonDto;
import com.web.security.oauth2.dto.UserOverviewDto;

/**
 * The Interface UserService.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public interface UserService extends UserDetailsService {

  /**
   * Load current user json dto.
   *
   * @return the user json dto
   */
  UserJsonDto loadCurrentUserJsonDto();

  /**
   * Load user overview dto.
   *
   * @param overviewDto
   *          the overview dto
   * @return the user overview dto
   */
  UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

  /**
   * Checks if is existed username.
   *
   * @param username
   *          the username
   * @return true, if is existed username
   */
  boolean isExistedUsername(String username);

  /**
   * Save user.
   *
   * @param formDto
   *          the form dto
   * @return the string
   */
  String saveUser(UserFormDto formDto);
}
