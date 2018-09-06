/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import java.util.UUID;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * The Class GuidGeneratorUtils.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public final class GuidGeneratorUtils {

  /** The default client secret generator. */
  private static RandomValueStringGenerator defaultClientSecretGenerator =
      new RandomValueStringGenerator(32);

  /**
   * private constructor.
   */
  private GuidGeneratorUtils() {
    // do nothing
  }

  /**
   * Generate.
   *
   * @return the string
   */
  public static String generate() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * Generate client secret.
   *
   * @return the string
   */
  public static String generateClientSecret() {
    return defaultClientSecretGenerator.generate();
  }

}
