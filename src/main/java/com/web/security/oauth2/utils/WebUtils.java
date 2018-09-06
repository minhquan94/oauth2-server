/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class WebUtils.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public final class WebUtils {

  /** The Constant UTF_8. */
  public static final String UTF_8 = "UTF-8";

  /** The ip thread local. */
  private static ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

  /**
   * Instantiates a new web utils.
   */
  private WebUtils() {
    // do nothing
  }

  /**
   * Sets the ip.
   *
   * @param ip
   *          the new ip
   */
  public static void setIp(String ip) {
    ipThreadLocal.set(ip);
  }

  /**
   * Gets the ip.
   *
   * @return the ip
   */
  public static String getIp() {
    return ipThreadLocal.get();
  }

  /**
   * Retrieve client ip address.
   *
   * @param request
   *          HttpServletRequest
   * @return IP
   */
  public static String retrieveClientIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (isUnAvailableIp(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (isUnAvailableIp(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (isUnAvailableIp(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * Checks if is un available ip.
   *
   * @param ip
   *          the ip
   * @return true, if is un available ip
   */
  private static boolean isUnAvailableIp(String ip) {
    return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
  }

}
