/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * The Class MobileResourceServerConfiguration.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
@EnableResourceServer
public class MobileResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  /** The Constant RESOURCE_ID. */
  public static final String RESOURCE_ID = "oauth2-resource";

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer)
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        // Since we want the protected resources to be accessible in the UI as
        // well we need
        // session creation to be allowed (it's disabled by default in 2.0.6)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .requestMatchers().antMatchers("/m/**").and().authorizeRequests().antMatchers("/m/**")
        .access("#oauth2.hasScope('read') and hasRole('ROLE_MOBILE')");

  }

}
