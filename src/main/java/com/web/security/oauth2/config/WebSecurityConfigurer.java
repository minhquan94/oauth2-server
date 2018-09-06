/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.web.security.oauth2.service.UserService;

/**
 * The Class WebSecurityConfigurer.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  /** The user service. */
  @Autowired
  private UserService userService;

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
   */
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    // Ignore, public
    web.ignoring().antMatchers("/public/**", "/static/**");
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/oauth/authorize", "/oauth/token", "/oauth/rest_token");
    http.authorizeRequests().antMatchers("/public/**").permitAll().antMatchers("/static/**")
        .permitAll().antMatchers("/oauth/rest_token*").permitAll().antMatchers("/login*")
        .permitAll().antMatchers("/user/**").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/login*").anonymous().anyRequest().authenticated().and()
        .formLogin().loginPage("/login").loginProcessingUrl("/signin")
        .failureUrl("/login?error=1").usernameParameter("oidc_user")
        .passwordParameter("oidcPwd").and().logout().logoutUrl("/signout")
        .deleteCookies("JSESSIONID").logoutSuccessUrl("/").and().exceptionHandling();
    http.authenticationProvider(authenticationProvider());
  }

  /**
   * Authentication provider.
   *
   * @return the authentication provider
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider daoAuthenticationProvider =
        new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  /**
   * BCrypt.
   *
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
