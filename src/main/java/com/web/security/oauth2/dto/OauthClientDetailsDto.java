/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.web.security.oauth2.domain.entity.OauthClientDetailsEntity;
import com.web.security.oauth2.utils.DateUtils;
import com.web.security.oauth2.utils.GuidGeneratorUtils;
import com.web.security.oauth2.utils.PasswordCodecUtils;

/**
 * The Class OauthClientDetailsDto.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class OauthClientDetailsDto implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4011292111995231569L;

  /** The create time. */
  private String createTime;

  /** The archived. */
  private boolean archived;

  /** The client id. */
  private String clientId = GuidGeneratorUtils.generate();

  /** The resource ids. */
  private String resourceIds;

  /** The client secret. */
  private String clientSecret = GuidGeneratorUtils.generateClientSecret();

  /** The scope. */
  private String scope;

  /** The authorized grant types. */
  private String authorizedGrantTypes;

  /** The web server redirect uri. */
  private String webServerRedirectUri;

  /** The authorities. */
  private String authorities;

  /** The access token validity. */
  private Integer accessTokenValidity;

  /** The refresh token validity. */
  private Integer refreshTokenValidity;

  /** The additional information. */
  // optional
  private String additionalInformation;

  /** The trusted. */
  private boolean trusted;

  /**
   * Instantiates a new oauth client details dto.
   */
  public OauthClientDetailsDto() {
    // do nothing
  }

  /**
   * Instantiates a new oauth client details dto.
   *
   * @param clientDetails
   *          the client details
   */
  public OauthClientDetailsDto(OauthClientDetailsEntity clientDetails) {
    this.clientId = clientDetails.getClientId();
    this.clientSecret = clientDetails.getClientSecret();
    this.scope = clientDetails.getScope();

    this.createTime = DateUtils.toDateTime(clientDetails.getCreateTime().toLocalDateTime());
    this.archived = clientDetails.isArchived();
    this.resourceIds = clientDetails.getResourceIds();

    this.webServerRedirectUri = clientDetails.getWebServerRedirectUri();
    this.authorities = clientDetails.getAuthorities();
    this.accessTokenValidity = clientDetails.getAccessTokenValidity();

    this.refreshTokenValidity = clientDetails.getRefreshTokenValidity();
    this.additionalInformation = clientDetails.getAdditionalInformation();
    this.trusted = clientDetails.isTrusted();

    this.authorizedGrantTypes = clientDetails.getAuthorizedGrantTypes();
  }

  /**
   * Gets the creates the time.
   *
   * @return the creates the time
   */
  public String getCreateTime() {
    return createTime;
  }

  /**
   * Sets the creates the time.
   *
   * @param createTime
   *          the new creates the time
   */
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  /**
   * Checks if is archived.
   *
   * @return true, if is archived
   */
  public boolean isArchived() {
    return archived;
  }

  /**
   * Sets the archived.
   *
   * @param archived
   *          the new archived
   */
  public void setArchived(boolean archived) {
    this.archived = archived;
  }

  /**
   * Gets the client id.
   *
   * @return the client id
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * Sets the client id.
   *
   * @param clientId
   *          the new client id
   */
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   * Gets the resource ids.
   *
   * @return the resource ids
   */
  public String getResourceIds() {
    return resourceIds;
  }

  /**
   * Sets the resource ids.
   *
   * @param resourceIds
   *          the new resource ids
   */
  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  /**
   * Gets the client secret.
   *
   * @return the client secret
   */
  public String getClientSecret() {
    return clientSecret;
  }

  /**
   * Sets the client secret.
   *
   * @param clientSecret
   *          the new client secret
   */
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  /**
   * Gets the scope.
   *
   * @return the scope
   */
  public String getScope() {
    return scope;
  }

  /**
   * Gets the scope with blank.
   *
   * @return the scope with blank
   */
  public String getScopeWithBlank() {
    if (scope != null && scope.contains(",")) {
      return scope.replaceAll(",", " ");
    }
    return scope;
  }

  /**
   * Sets the scope.
   *
   * @param scope
   *          the new scope
   */
  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Gets the authorized grant types.
   *
   * @return the authorized grant types
   */
  public String getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  /**
   * Sets the authorized grant types.
   *
   * @param authorizedGrantTypes
   *          the new authorized grant types
   */
  public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  /**
   * Gets the web server redirect uri.
   *
   * @return the web server redirect uri
   */
  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  /**
   * Sets the web server redirect uri.
   *
   * @param webServerRedirectUri
   *          the new web server redirect uri
   */
  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  /**
   * Gets the authorities.
   *
   * @return the authorities
   */
  public String getAuthorities() {
    return authorities;
  }

  /**
   * Sets the authorities.
   *
   * @param authorities
   *          the new authorities
   */
  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  /**
   * Gets the access token validity.
   *
   * @return the access token validity
   */
  public Integer getAccessTokenValidity() {
    return accessTokenValidity;
  }

  /**
   * Sets the access token validity.
   *
   * @param accessTokenValidity
   *          the new access token validity
   */
  public void setAccessTokenValidity(Integer accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  /**
   * Gets the refresh token validity.
   *
   * @return the refresh token validity
   */
  public Integer getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  /**
   * Sets the refresh token validity.
   *
   * @param refreshTokenValidity
   *          the new refresh token validity
   */
  public void setRefreshTokenValidity(Integer refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  /**
   * Gets the additional information.
   *
   * @return the additional information
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * Sets the additional information.
   *
   * @param additionalInformation
   *          the new additional information
   */
  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  /**
   * Checks if is trusted.
   *
   * @return true, if is trusted
   */
  public boolean isTrusted() {
    return trusted;
  }

  /**
   * Sets the trusted.
   *
   * @param trusted
   *          the new trusted
   */
  public void setTrusted(boolean trusted) {
    this.trusted = trusted;
  }

  /**
   * To dtos.
   *
   * @param clientDetailses
   *          the client detailses
   * @return the list
   */
  public static List<OauthClientDetailsDto> toDtos(
      List<OauthClientDetailsEntity> clientDetailses) {
    final List<OauthClientDetailsDto> dtos = new ArrayList<>(clientDetailses.size());
    for (final OauthClientDetailsEntity clientDetailse : clientDetailses) {
      dtos.add(new OauthClientDetailsDto(clientDetailse));
    }
    return dtos;
  }

  /**
   * Checks if is contains authorization code.
   *
   * @return true, if is contains authorization code
   */
  public boolean isContainsAuthorizationCode() {
    return this.authorizedGrantTypes.contains("authorization_code");
  }

  /**
   * Checks if is contains password.
   *
   * @return true, if is contains password
   */
  public boolean isContainsPassword() {
    return this.authorizedGrantTypes.contains("password");
  }

  /**
   * Checks if is contains implicit.
   *
   * @return true, if is contains implicit
   */
  public boolean isContainsImplicit() {
    return this.authorizedGrantTypes.contains("implicit");
  }

  /**
   * Checks if is contains client credentials.
   *
   * @return true, if is contains client credentials
   */
  public boolean isContainsClientCredentials() {
    return this.authorizedGrantTypes.contains("client_credentials");
  }

  /**
   * Checks if is contains refresh token.
   *
   * @return true, if is contains refresh token
   */
  public boolean isContainsRefreshToken() {
    return this.authorizedGrantTypes.contains("refresh_token");
  }

  /**
   * Creates the domain.
   *
   * @return the oauth client details
   */
  public OauthClientDetailsEntity createDomain() {
    final OauthClientDetailsEntity clientDetails = new OauthClientDetailsEntity();
    clientDetails.setClientId(clientId);
    clientDetails.setClientSecret(PasswordCodecUtils.encode(clientSecret));
    clientDetails.setResourceIds(resourceIds);
    clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
    clientDetails.setScope(scope);

    if (StringUtils.isNotEmpty(webServerRedirectUri)) {
      clientDetails.setWebServerRedirectUri(webServerRedirectUri);
    }

    if (StringUtils.isNotEmpty(authorities)) {
      clientDetails.setAuthorities(authorities);
    }

    clientDetails.setAccessTokenValidity(accessTokenValidity);
    clientDetails.setRefreshTokenValidity(refreshTokenValidity);
    clientDetails.setTrusted(trusted);

    if (StringUtils.isNotEmpty(additionalInformation)) {
      clientDetails.setAdditionalInformation(additionalInformation);
    }
    return clientDetails;
  }
}
