/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import java.util.ArrayList;
import java.util.List;

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

import com.web.security.oauth2.domain.Privilege;
import com.web.security.oauth2.domain.entity.UserEntity;
import com.web.security.oauth2.domain.entity.UserPrivilegeEntity;
import com.web.security.oauth2.domain.repository.UserPrivilegeRepository;
import com.web.security.oauth2.domain.repository.UserRepository;
import com.web.security.oauth2.service.UserService;
import com.web.security.oauth2.utils.DateUtils;

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

  /** The user repository. */
  @Autowired
  private UserRepository userRepository;

  /** The user privilege repository. */
  @Autowired
  private UserPrivilegeRepository userPrivilegeRepository;

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
    web.ignoring().antMatchers("/h2-console/**");
    web.ignoring().antMatchers("/favicon.ico");
    web.ignoring().antMatchers("/content/**");
    web.ignoring().antMatchers("/i18n/**");
    web.ignoring().antMatchers("/swagger-ui/**");
    web.ignoring().antMatchers("/*.js").antMatchers("/js/*.js");
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
        .permitAll().antMatchers("/oauth/rest_token*").permitAll()
        .antMatchers("/login*", "/account/register", "/account/login").permitAll()
        .antMatchers("/h2-console/**").hasAnyRole("ADMIN").antMatchers("/user/**")
        .hasAnyRole("ADMIN").antMatchers(HttpMethod.GET, "/login*").anonymous().anyRequest()
        .authenticated().and().formLogin().loginPage("/login").loginProcessingUrl("/signin")
        .failureUrl("/login?error=1").usernameParameter("oidc_user")
        .passwordParameter("oidcPwd").and().logout().logoutUrl("/signout")
        .logoutSuccessUrl("/").and().exceptionHandling().and().httpBasic();
    http.authenticationProvider(authenticationProvider());
  }

  /**
   * Authentication provider.
   *
   * @return the authentication provider
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    initUserDefaut();
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

  /**
   * Inits the user defaut.
   */
  private void initUserDefaut() {
    final UserEntity user1 = new UserEntity();
    user1.setUsername("admin");
    user1.setPassword(passwordEncoder().encode("admin"));
    user1.setEmail("admin@zuan.com");
    user1.setEnabled(true);
    user1.setCreateTime(DateUtils.currentTimestamp());
    user1.setDefaultUser(true);

    final UserEntity user2 = new UserEntity();
    user2.setUsername("user");
    user2.setPassword(passwordEncoder().encode("user"));
    user2.setEmail("user@zuan.com");
    user2.setEnabled(true);
    user2.setCreateTime(DateUtils.currentTimestamp());
    user2.setDefaultUser(true);

    final List<UserEntity> userEntities = new ArrayList<>();
    final List<UserPrivilegeEntity> privilegeEntities = new ArrayList<>();
    if (userRepository.findByUsername("admin") == null) {
      userEntities.add(user1);

      final UserPrivilegeEntity privilegeEntity1 = new UserPrivilegeEntity();
      privilegeEntity1.setUser(user1);
      privilegeEntity1.setPrivilege(Privilege.ADMIN);
      privilegeEntities.add(privilegeEntity1);

      final UserPrivilegeEntity privilegeEntity2 = new UserPrivilegeEntity();
      privilegeEntity2.setUser(user1);
      privilegeEntity2.setPrivilege(Privilege.USER);
      privilegeEntities.add(privilegeEntity2);
    }

    if (userRepository.findByUsername("user") == null) {
      userEntities.add(user2);

      final UserPrivilegeEntity privilegeEntity3 = new UserPrivilegeEntity();
      privilegeEntity3.setUser(user2);
      privilegeEntity3.setPrivilege(Privilege.USER);
      privilegeEntities.add(privilegeEntity3);
    }
    userRepository.saveAll(userEntities);
    userPrivilegeRepository.saveAll(privilegeEntities);
  }
}
