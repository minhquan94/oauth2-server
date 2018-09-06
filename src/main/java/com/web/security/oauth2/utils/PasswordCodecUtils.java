/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class PasswordCodecUtils.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public final class PasswordCodecUtils {

  /**
   * Instantiates a new password handler.
   */
  private PasswordCodecUtils() {
    // do nothing
  }

  /**
   * Encode.
   *
   * @param password
   *          the password
   * @return the string
   */
  public static String encode(String password) {
    final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }
}
