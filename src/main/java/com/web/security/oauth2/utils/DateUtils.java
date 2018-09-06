/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The Class DateUtils.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public abstract class DateUtils {

  /** The Constant DEFAULT_DATE_TIME_FORMAT. */
  public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /**
   * Private constructor.
   */
  private DateUtils() {
    // do nothing
  }

  /**
   * Now.
   *
   * @return the local date time
   */
  public static LocalDateTime now() {
    return LocalDateTime.now();
  }

  /**
   * To date time.
   *
   * @param date
   *          the date
   * @return the string
   */
  public static String toDateTime(LocalDateTime date) {
    return toDateTime(date, DEFAULT_DATE_TIME_FORMAT);
  }

  /**
   * To date time.
   *
   * @param dateTime
   *          the date time
   * @param pattern
   *          the pattern
   * @return the string
   */
  public static String toDateTime(LocalDateTime dateTime, String pattern) {
    return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
  }

  /**
   * To date text.
   *
   * @param date
   *          the date
   * @param pattern
   *          the pattern
   * @return the string
   */
  public static String toDateText(LocalDate date, String pattern) {
    if (date == null || pattern == null) {
      return null;
    }
    return date.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
  }

}
