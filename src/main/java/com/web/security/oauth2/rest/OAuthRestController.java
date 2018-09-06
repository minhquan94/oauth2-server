/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.rest;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class OAuthRestController.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Controller
public class OAuthRestController implements InitializingBean, ApplicationContextAware {

  /** The Constant HANDLING_ERROR. */
  private static final String HANDLING_ERROR = "Handling error: ";

  /** The Constant CLIENT_CREDENTIALS. */
  private static final String GRANT_CLIENT_CREDENTIALS = "client_credentials";

  /** The Constant IMPLICIT. */
  private static final String GRANT_IMPLICIT = "implicit";

  /** The Constant AUTHORIZATION_CODE. */
  private static final String GRANT_AUTHORIZATION_CODE = "authorization_code";

  /** The Constant GRANT_PASSWORD. */
  private static final String GRANT_PASSWORD = "password";

  /** The Constant REFRESH_TOKEN. */
  private static final String GRANT_REFRESH_TOKEN = "refresh_token";

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(OAuthRestController.class);

  /** The client details service. */
  @Autowired
  private ClientDetailsService clientDetailsService;

  /** The token services. */
  @Autowired
  @Qualifier("defaultAuthorizationServerTokenServices")
  private AuthorizationServerTokenServices tokenServices;

  /** The authorization code services. */
  @Autowired
  private AuthorizationCodeServices authorizationCodeServices;

  /** The authentication manager. */
  private AuthenticationManager authenticationManager;

  /** The o auth2 request factory. */
  private OAuth2RequestFactory oAuth2RequestFactory;

  /** The o auth2 request validator. */
  private final OAuth2RequestValidator oAuth2RequestValidator =
      new DefaultOAuth2RequestValidator();

  /** The provider exception handler. */
  private final WebResponseExceptionTranslator<OAuth2Exception> providerExceptionHandler =
      new DefaultWebResponseExceptionTranslator();

  /**
   * Post access token.
   *
   * @param parameters
   *          the parameters
   * @return the o auth2 access token
   */
  @RequestMapping(value = "/oauth/rest_token", method = RequestMethod.POST)
  @ResponseBody
  public OAuth2AccessToken postAccessToken(@RequestBody Map<String, String> parameters) { // NOSONAR
    final String clientId = getClientId(parameters);
    final ClientDetails authenticatedClient =
        clientDetailsService.loadClientByClientId(clientId);
    final TokenRequest tokenRequest =
        oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);
    if (clientId != null && !StringUtils.equals(clientId, "")
        && !StringUtils.equals(clientId, tokenRequest.getClientId())) {
      /*
       * Only validate the client details if a client authenticated during this
       * request. double check to make sure that the client ID in the token
       * request is the same as that in the authenticated client
       */
      throw new InvalidClientException("Given client ID does not match authenticated client");
    }
    if (authenticatedClient != null) {
      oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
    }
    final String grantType = tokenRequest.getGrantType();
    if (StringUtils.isBlank(grantType)) {
      throw new InvalidRequestException("Missing grant type");
    }
    if (StringUtils.equals(grantType, GRANT_IMPLICIT)) {
      throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
    }
    if (isAuthCodeRequest(parameters) && !tokenRequest.getScope().isEmpty()) {
      // The scope was requested or determined during the authorization step
      LOG.debug("Clearing scope of incoming token request");
      tokenRequest.setScope(Collections.<String> emptySet());
    }
    if (isRefreshTokenRequest(parameters)) {
      // A refresh token has its own default scopes, so we should ignore any
      // added by the factory here.
      tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
    }
    final OAuth2AccessToken token = getTokenGranter(grantType).grant(grantType, tokenRequest);
    if (token == null) {
      throw new UnsupportedGrantTypeException("Unsupported grant type: " + grantType);
    }
    return token;

  }

  /**
   * Gets the token granter.
   *
   * @param grantType
   *          the grant type
   * @return the token granter
   */
  protected TokenGranter getTokenGranter(String grantType) { // NOSONAR
    if (StringUtils.equals(GRANT_AUTHORIZATION_CODE, grantType)) {
      return new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices,
          clientDetailsService, this.oAuth2RequestFactory);
    } else if (StringUtils.equals(GRANT_PASSWORD, grantType)) {
      return new ResourceOwnerPasswordTokenGranter(getAuthenticationManager(), tokenServices,
          clientDetailsService, this.oAuth2RequestFactory);
    } else if (StringUtils.equals(GRANT_REFRESH_TOKEN, grantType)) {
      return new RefreshTokenGranter(tokenServices, clientDetailsService,
          this.oAuth2RequestFactory);
    } else if (StringUtils.equals(GRANT_CLIENT_CREDENTIALS, grantType)) {
      return new ClientCredentialsTokenGranter(tokenServices, clientDetailsService,
          this.oAuth2RequestFactory);
    } else if (StringUtils.equals(GRANT_IMPLICIT, grantType)) {
      return new ImplicitTokenGranter(tokenServices, clientDetailsService,
          this.oAuth2RequestFactory);
    } else {
      throw new UnsupportedGrantTypeException("Unsupport grant_type: " + grantType);
    }
  }

  /**
   * Handle exception.
   *
   * @param e
   *          the e
   * @return the response entity
   * @throws Exception
   *           the exception
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception { // NOSONAR
    LOG.info(HANDLING_ERROR + e.getClass().getSimpleName() + ", " + e.getMessage());
    return getExceptionTranslator().translate(e);
  }

  /**
   * Handle client registration exception.
   *
   * @param e
   *          the e
   * @return the response entity
   * @throws Exception
   *           the exception
   */
  @ExceptionHandler(ClientRegistrationException.class)
  public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e)
      throws Exception { // NOSONAR
    LOG.info(HANDLING_ERROR + e.getClass().getSimpleName() + ", " + e.getMessage());
    return getExceptionTranslator().translate(new BadClientCredentialsException());
  }

  /**
   * Handle exception.
   *
   * @param e
   *          the e
   * @return the response entity
   * @throws Exception
   *           the exception
   */
  @ExceptionHandler(OAuth2Exception.class)
  public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
    LOG.info(HANDLING_ERROR + e.getClass().getSimpleName() + ", " + e.getMessage());
    return getExceptionTranslator().translate(e);
  }

  /**
   * Checks if is refresh token request.
   *
   * @param parameters
   *          the parameters
   * @return true, if is refresh token request
   */
  private boolean isRefreshTokenRequest(Map<String, String> parameters) {
    return GRANT_REFRESH_TOKEN.equals(parameters.get("grant_type"))
        && parameters.get(GRANT_REFRESH_TOKEN) != null;
  }

  /**
   * Checks if is auth code request.
   *
   * @param parameters
   *          the parameters
   * @return true, if is auth code request
   */
  private boolean isAuthCodeRequest(Map<String, String> parameters) {
    return GRANT_AUTHORIZATION_CODE.equals(parameters.get("grant_type"))
        && parameters.get("code") != null;
  }

  /**
   * Gets the client id.
   *
   * @param parameters
   *          the parameters
   * @return the client id
   */
  protected String getClientId(Map<String, String> parameters) {
    return parameters.get("client_id");
  }

  /**
   * Gets the authentication manager.
   *
   * @return the authentication manager
   */
  private AuthenticationManager getAuthenticationManager() {
    return this.authenticationManager;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() throws Exception {

    Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
    Assert.state(authenticationManager != null, "AuthenticationManager must be provided");

    oAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
  }

  /**
   * Gets the exception translator.
   *
   * @return the exception translator
   */
  protected WebResponseExceptionTranslator<OAuth2Exception> getExceptionTranslator() {
    return providerExceptionHandler;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    if (this.authenticationManager == null) {
      this.authenticationManager =
          (AuthenticationManager) applicationContext.getBean("authenticationManagerBean");
    }
  }
}
