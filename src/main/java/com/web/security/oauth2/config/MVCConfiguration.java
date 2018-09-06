/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.security.oauth2.utils.WebUtils;

/**
 * The Class MVCConfiguration.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

  /**
   * Adds the interceptors.
   *
   * @param registry
   *          the registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebMvcConfigurer.super.addInterceptors(registry);
  }

  /**
   * Configure message converters.
   *
   * @param converters
   *          the converters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter< ? >> converters) {
    WebMvcConfigurer.super.configureMessageConverters(converters);
    converters.add(new StringHttpMessageConverter(Charset.forName(WebUtils.UTF_8)));
  }
}
