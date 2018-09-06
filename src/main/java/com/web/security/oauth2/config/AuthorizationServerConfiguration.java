/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.web.security.oauth2.domain.CustomJdbcClientDetailsService;
import com.web.security.oauth2.service.OauthService;
import com.web.security.oauth2.service.UserService;
import com.web.security.oauth2.utils.OauthUserApprovalUtils;

/**
 * The Class AuthorizationServerConfiguration.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  /** The token store. */
  @Autowired
  private TokenStore tokenStore;

  /** The client details service. */
  @Autowired
  private ClientDetailsService clientDetailsService;

  /** The oauth service. */
  @Autowired
  private OauthService oauthService;

  /** The authorization code services. */
  @Autowired
  private AuthorizationCodeServices authorizationCodeServices;

  /** The user details service. */
  @Autowired
  private UserService userDetailsService;

  /** The authentication manager. */
  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

  /**
   * Token store.
   *
   * @param dataSource
   *          the data source
   * @return the token store
   */
  @Bean
  public TokenStore tokenStore(DataSource dataSource) {
    return new JdbcTokenStore(dataSource);
  }

  /**
   * Client details service.
   *
   * @param dataSource
   *          the data source
   * @return the client details service
   */
  @Bean
  public ClientDetailsService clientDetailsService(DataSource dataSource) {
    return new CustomJdbcClientDetailsService(dataSource);
  }

  /**
   * Authorization code services.
   *
   * @param dataSource
   *          the data source
   * @return the authorization code services
   */
  @Bean
  public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
    return new JdbcAuthorizationCodeServices(dataSource);
  }

  /**
   * Data source initializer.
   *
   * @param dataSource
   *          the data source
   * @return the data source initializer
   */
  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    final DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    return initializer;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore).authorizationCodeServices(authorizationCodeServices)
        .userDetailsService(userDetailsService).userApprovalHandler(userApprovalHandler())
        .authenticationManager(authenticationManager);
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.realm("oauth2-server").allowFormAuthenticationForClients();
  }

  /**
   * O auth2 request factory.
   *
   * @return the o auth2 request factory
   */
  @Bean
  public OAuth2RequestFactory oAuth2RequestFactory() {
    return new DefaultOAuth2RequestFactory(clientDetailsService);
  }

  /**
   * User approval handler.
   *
   * @return the user approval handler
   */
  @Bean
  public UserApprovalHandler userApprovalHandler() {
    final OauthUserApprovalUtils userApprovalHandler = new OauthUserApprovalUtils();
    userApprovalHandler.setOauthService(oauthService);
    userApprovalHandler.setTokenStore(tokenStore);
    userApprovalHandler.setClientDetailsService(this.clientDetailsService);
    userApprovalHandler.setRequestFactory(oAuth2RequestFactory());
    return userApprovalHandler;
  }
}
