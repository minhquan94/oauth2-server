/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

/**
 * The Class CustomErrorType.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class CustomErrorType {

  /** The error message. */
  private final String errorMessage;

  /**
   * Instantiates a new custom error type.
   *
   * @param errorMessage
   *          the error message
   */
  public CustomErrorType(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  public String getErrorMessage() {
    return errorMessage;
  }

}
